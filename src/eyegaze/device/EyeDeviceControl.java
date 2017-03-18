package eyegaze.device;

import eyegaze.jni.EyeGazeJNI;

/**
 * The functions related to interact with eye gaze device
 * @author EYEGAZE 2.3 i7
 *
 */
public class EyeDeviceControl {
	
	EyeGazeJNI eyeGaze = new EyeGazeJNI();
	
	private static EyeDeviceControl control;
	
	private long initTime;
	
	public static EyeDeviceControl getInstance() {
		if(control == null) {
			control = new EyeDeviceControl();
		}
		return control;
	}
	
	/*
	 * Initialize device and start calibrate process
	 */
	public int initializeDevice() {
		int result = eyeGaze.EyeGazeInit(0);
		initTime = System.currentTimeMillis();
		if(result == 0) {
			eyeGaze.Calibrate();
			System.out.print("start device....." + result +"\n'");
		}
		return result;
	}
	
	/**
	 * Start logging the trace data in "trace.dat" file
	 * @return
	 */
	public int startLogging() {
		int star = eyeGaze.EyeGazeLogStart();
		if(star == 0) {
			System.out.print("start logging data in trace.dat file.....\n");
		}
		return star;
	}
	
	/**
	 * Stop logging trace data
	 * @return
	 */
	public int stopLogging() {
		int star = eyeGaze.EyeGazeLogStop();
		if(star == 0) {
			System.out.print("stop logging.....\n");
		}
		return star;
	}

	/**
	 * Shut down the eye gaze device
	 * @return
	 */
	public int shutdonwDevice() {
		int star = eyeGaze.EyeGazeShutDown();
		if(star == 0) {
			System.out.print("device is shutted down.....\n");
		}
		return star;
	}
	
	/**
	 * Get the time when EgInit() function has been called
	 * @return
	 */
	public long getDeviceInitTime() {
		return initTime;
	}
}
