package eyegaze.device;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import eyegaze.jni.EyeGazeData;

public class RetrieveDataThread extends Thread {
	
	ScheduledExecutorService executor;
	
	public void run() {
		Runnable helloRunnable = new Runnable() {
		    public void run() {
		        System.out.println("JAVA log: Start to fetch data.....");
		        EyeGazeData data = EyeDeviceControl.getInstance().getEyeGazeData();
        		if(data != null) {
        			System.out.println("JAVA log: data getiIGaze....." + data.getiIGaze());
        			System.out.println("JAVA log: data getfXEyeballOffsetMm....." + data.getfXEyeballOffsetMm());
        			System.out.println("JAVA log: data getiJGaze....." + data.getiJGaze());
        			System.out.println("JAVA log: data getCameraFieldCount....." + data.getCameraFieldCount());
        		}else {
        			System.out.println("JAVA log: no data collected.....");
        		}
		    }
		};

		executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(helloRunnable, 0, 300, TimeUnit.MICROSECONDS);
	}
	
	//Need to stop request eye gaze data after the thread stop
	public void destory() {
		executor.shutdown();
		System.out.println("JAVA log: Fetch data thread stopped.....");
	}

}
