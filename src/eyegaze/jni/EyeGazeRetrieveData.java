package eyegaze.jni;

import java.util.ArrayList;
import java.util.List;

import eyegaze.gui.SoftKeyBoardMain;
import eyegaze.gui.model.FixationModel;

/**
 * JNI callback class C++ will call receivegazedata() to pass the gaze data to
 * Java application DO NOT modify the class name and function name
 * 
 * @author EYEGAZE 2.3 i7
 *
 */
public class EyeGazeRetrieveData {

	/*
	 * 6 gaze data marked as one fixation
	 */
	public static int miniFixationSize;

	public static int fixationStartMiniSize;

	/*
	 * Current saved fixation data
	 */
	static FixationModel model = null;

	private static int currentIndex = 0;

	private static boolean isNotified = false;

	private static List<EyeGazeData> rawData = new ArrayList<EyeGazeData>();

	/*
	 * The same fixation gaze data, if the size is larger than miniFixationSize,
	 * the those data could be marked as one fixation
	 */
	static List<FixationModel> fixationList = null;

	/**
	 * Receive gaze point data from eye gaze Device It would be called in DLL
	 * Every time C++ received one data, it would pass it to the function
	 * 
	 * @param data
	 */

	// NOTE on 3.21 The offset between two data is 0.0083 secs
	public static void receivegazedaata(EyeGazeData data) {
		if (data != null) {
			// Used in written log
			rawData.add(data);
			int pupilXPos = data.getiIGaze();
			int pupilYPos = data.getiJGaze();
//			System.out.println("pupilYPos......."+pupilYPos);
//			System.out.println("detect gaze......."+data.isGazeVectorFound());
			/*
			 * If current data is the first point of fixation verification
			 */
			if (model == null) {
				//System.out.println("first model.......");
				model = convertEyeGazeDataToFixation(data);
				//System.out.println("first model......." + model.getxEyeballMm());
				model.setFixationIndex(currentIndex);
				fixationList = new ArrayList<FixationModel>();
				fixationList.add(model);
			} else {
				/*
				 * If the fixation list size is bigger than 0 but small than 6
				 */
				System.out.println("not the first model.......");
				boolean isSame = model.isSameFixation(pupilXPos, pupilYPos);
				if (isSame) {
					System.out.println("same fixation as last time......");
					// If it belongs to the same fixation, add to the fixation
					// list
					FixationModel nextMo = convertEyeGazeDataToFixation(data);
					fixationList.add(nextMo);
//					if(fixationList.size() > 6) {
//						SoftKeyBoardMain.getInstance().addFocusCircle(pupilXPos, pupilXPos);
//						System.out.println("Added circle at position: pupilXPos......."+pupilXPos +"," +pupilYPos);
//					}
					// System.out.println("fixationList "+fixationList.size());
					// System.out.println("miniFixationSize/3
					// "+miniFixationSize/3);
					// if the current list size is larger than 6, then marked as
					// one fixation
					if (fixationList.size() >= miniFixationSize / 3) {
						// If it is the first time to reach 6 gaze points,
						// notify the application,
						// Otherwise just add the list
						if (!isNotified) {
							System.out.println("Detecting one fixation data:Notifying the application");

							SoftKeyBoardMain.getInstance().getKeyByPosition(model.getxPosition(), model.getyPosition());
							// Set fixation index and plus the currentIndex
							nextMo.setFixationIndex(currentIndex);
							// The current index could only be plus after we
							// detect one fixation
							currentIndex++;
							isNotified = true;
						}
						if (model.getyPosition() > 576) {
							// means the gaze point is on setting key
							// the button will be clicked when the fixation
							// points are more than 100 or more
							if (fixationList.size() > 150) {
								SoftKeyBoardMain.getInstance().isActiveSetting(true);
							}

						}
						// System.out.println("Detecting one fixation:Already
						// notified, DO NOTHING"+ fixationList.size());
						// TODO
						// Notify the application that we have received one
						// fixation data
						// And prepare to write into the dat file
					}
				} else {
					// System.out.println("not the same fixation model.......");
					SoftKeyBoardMain.getInstance().stopProgressBarTimer();
					/*
					 * Empty the previous list and create a new possible
					 * fixation point
					 */
					model = convertEyeGazeDataToFixation(data);
					fixationList = new ArrayList<FixationModel>();
					fixationList.add(model);
					isNotified = false;
				}
			}
			// System.out.println("Detecting one fixation data: Current fixation
			// Index :" + currentIndex);
		} else {
			// System.out.println("DID NOT receive data");
		}
	}

	/*
	 * Convert the gaze data to java fixationModel
	 */
	private static FixationModel convertEyeGazeDataToFixation(EyeGazeData data) {
		FixationModel newFix = new FixationModel();
		newFix.setIsEyeVectorFound(data.isGazeVectorFound());
		newFix.setxPosition(data.getiIGaze());
		newFix.setyPosition(data.getiJGaze());
		newFix.setPupilDiam(data.getPupilRadiusMm());
		newFix.setGazeTime(data.getGazeTimeSec());
		newFix.setFocusRangeMm(data.getFocusRangeImageTime());
		newFix.setFocusRangeoffsetMm(data.getFoucsRangeOffsetMm());
		newFix.setxEyeballMm(data.getfXEyeballOffsetMm());
		newFix.setyEyeballMm(data.getfYEyeballOffsetMm());
		newFix.setFixationIndex(currentIndex);
		return newFix;
	}

	/*
	 * Write the fixation log when application exits. By calling the verify
	 * fixtaion function from JNI
	 */
	public static void writeFixationDataLog() {
		EyeGazeData[] rawDataArray = new EyeGazeData[rawData.size()];
		for (int i = 0; i < rawData.size(); i++) {
			rawDataArray[i] = rawData.get(i);
		}
		EyeGazeJNI.getInstance().VerifyFixation(rawDataArray);
	}

}
