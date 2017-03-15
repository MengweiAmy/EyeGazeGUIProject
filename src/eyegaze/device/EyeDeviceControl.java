package eyegaze.device;

import eyegaze.jni.EyeGazeJNI;

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
	 * Initialize device and start calibrate
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
	
	public int startLogging() {
		int star = eyeGaze.EyeGazeLogStart();
		if(star == 0) {
			System.out.print("start logging data in trace.dat file.....\n");
		}
		return star;
	}
	
	public int stopLogging() {
		int star = eyeGaze.EyeGazeLogStop();
		if(star == 0) {
			System.out.print("stop logging.....\n");
		}
		return star;
	}

	public int shutdonwDevice() {
		int star = eyeGaze.EyeGazeShutDown();
		if(star == 0) {
			System.out.print("device is shutted down.....\n");
		}
		return star;
	}
	
	public long getDeviceInitTime() {
		return initTime;
	}
}
