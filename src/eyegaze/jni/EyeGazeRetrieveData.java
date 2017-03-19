package eyegaze.jni;

public class EyeGazeRetrieveData {
	
	public static void receivegazedaata(EyeGazeData data) {
		if(data != null) {
			 //System.out.println("Received gaze data !!!!");
			 
			// System.out.println("data xpos" + data.getiIGaze());
			 System.out.println("data ypos" + data.getiJGaze());
			 //System.out.println("data xpos" + data.getiIGaze());
			 //System.out.println("data xpos" + data.getiIGaze());
			 
		}else {
			System.out.println("DID NOT receive data");
		}
	}

}
