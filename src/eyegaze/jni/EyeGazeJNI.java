package eyegaze.jni;

public class EyeGazeJNI {
	
	 private static EyeGazeJNI jni;
	 
	 public static EyeGazeJNI getInstance() {
		if(jni == null) {
			jni = new EyeGazeJNI();
		}
		return jni;
	 }
	
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
	   
	   //Analysis the fixation data based on existed log
	   //If writelog parameter is 1, then write the fixation log
	   public native int VerifyFixation(EyeGazeData[] gazeDatalist);
	   
	   //Manually stop data collection
	   //It is necessary to be called before shut down the device
	   //Otherwise the device cannot be shutdown correctly
	   public native void StopDataCollection();
	   
	   //Initilise the eye gaze image function
	   public native void EyeGazeImageDisplay();

}
