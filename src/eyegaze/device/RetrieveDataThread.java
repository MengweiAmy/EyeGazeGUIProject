package eyegaze.device;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RetrieveDataThread extends Thread {
	
	ScheduledExecutorService executor;
	
	public void run() {
		Runnable helloRunnable = new Runnable() {
		    public void run() {
		        System.out.println("JAVA log: Start to fetch data.....");
		        /*
		         * Call C++ function in order to active the c++ callback function
		         */
		        EyeDeviceControl.getInstance().getEyeGazeData();
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
