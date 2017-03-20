package eyegaze.jni;

import java.util.ArrayList;
import java.util.List;

import eyegaze.gui.SoftKeyBoardMain;
import eyegaze.gui.model.FixationModel;

/**
 * JNI callback class
 * C++ will call receivegazedata() to pass the gaze data to Java application
 * DO NOT modify the class name and function name
 * @author EYEGAZE 2.3 i7
 *
 */
public class EyeGazeRetrieveData {
	
	/*
	 * 6 gaze data marked as one fixation
	 */
	private static int miniFixationSize = 20;
	
	/*
	 * Current saved fixation data
	 */
	static FixationModel model = null;
	
	private static int currentIndex = 0;
	
	private static boolean isNotified = false;
	
	/*
	 * The same fixation gaze data, 
	 * if the size is larger than miniFixationSize, the those data could be marked as one fixation
	 */
	static List<FixationModel> fixationList = null;
	
	/**
	 * Receive gaze point data from eye gaze Device
	 * It would be called in DLL
	 * Every time C++ received one data, it would pass it to the function
	 * @param data
	 */
	public static void receivegazedaata(EyeGazeData data) {
		if(data != null) {
			int pupilXPos = data.getiIGaze();
			int pupilYPos = data.getiJGaze();		
			
			/*
			 * If current data is the first point of fixation verification
			 */
			if(model == null) {
				model = convertEyeGazeDataToFixation(data);
				model.setFixationIndex(currentIndex);
				fixationList = new ArrayList<FixationModel>();
				fixationList.add(model);
			}else {
				/*
				 * If the fixation list size is bigger than 0 but small than 6
				 */
				boolean isSame = model.isSameFixation(pupilXPos, pupilYPos);
				if(isSame) {
					//If it belongs to the same fixation, add to the fixation list
					FixationModel nextMo = convertEyeGazeDataToFixation(data);
					fixationList.add(nextMo);
					// if the current list size is larger than 6, then marked as one fixation
					if(fixationList.size() >= miniFixationSize) {
						System.out.println("Detecting one fixation: fixation size :" + fixationList.size());
						System.out.println("Detecting one fixation: Raw Position X :" +model.getxPosition() + "Raw Position Y :" +model.getyPosition());
						
						System.out.println("Detecting one fixation: New data Position X :" +nextMo.getxPosition() + "New data Position Y :" +nextMo.getyPosition());
						//If it is the first time to reach 6 gaze points, notify the application,
						//Otherwise just add the list
						if(!isNotified) {
							System.out.println("Detecting one fixation data:Notifying the application");
							
							SoftKeyBoardMain.getInstance().getKeyByPosition(model.getxPosition(), model.getyPosition());
							//Set fixation index and plus the currentIndex
							nextMo.setFixationIndex(currentIndex);
							//The current index could only be plus after we detect one fixation
							currentIndex++;
							isNotified = true;
						}
						System.out.println("Detecting one fixation:Already notified, DO NOTHING"+ fixationList.size());
						//TODO
						//Notify the application that we have recevied one fixation data
						//And prepare to write into the dat file
					}else {
						
					}
				}else {
					/*
					 * Empty the previous list and create a new possible fixation point
					 */
					model = convertEyeGazeDataToFixation(data);
					fixationList = new ArrayList<FixationModel>();
					fixationList.add(model);
					isNotified = false;
				}
			}
			System.out.println("Detecting one fixation data: Current fixation Index :" + currentIndex);
		}else {
			System.out.println("DID NOT receive data");
		}
	}
	
	private static FixationModel convertEyeGazeDataToFixation(EyeGazeData data) {
		FixationModel newFix = new FixationModel();
		newFix.setxPosition(data.getiIGaze());
		newFix.setyPosition(data.getiJGaze());
		newFix.setPupilDiam(data.getPupilRadiusMm());
		return newFix;
	}
	
	public static void receivefixationdata(FixationIndex fixation) {
		if(fixation != null && fixation.getFixationIndex() != -1) {
			 //System.out.println("Received fixation data !!!!");
			 
			 //System.out.println("data xpos" + fixation.getxPos());
			 System.out.println("data ypos" + fixation.getyPos());
			 //System.out.println("data xpos" + data.getiIGaze());
			 
		}else {
			System.out.println("DID NOT receive data");
		}
	}

}
