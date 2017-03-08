import data.EyeGazeData;

public class EyeGazeJNI {
	
	 static {
	      System.loadLibrary("EyeGazeControlJNI"); // Load native library at runtime
	   }
	 
	   //Start the eye gaze device
	   public native int EyeGazeInit(int controlType);
	   
	   //Shut down the eye gaze device
	   public native int EyeGazeShutDown();
	   
	   //Calibrate the eye gaze device
	   public native int Calibrate();
	   
	   //Get the eye gaze data from the buffer
	   //TODO
	   public native EyeGazeData getEyeGazeData();
	   
	   //Start to log the gaze data
	   public native int EyeGazeLogStart();
	   
	   //Stop to log the gaze data, the file won't close unless the close() function is called
	   public native int EyeGazeLogStop();

}
