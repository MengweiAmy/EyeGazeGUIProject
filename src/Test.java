import java.util.Timer;
import java.util.TimerTask;

public class Test {
	
	   public static void main(String[] arg0s) {
		  EyeGazeJNI eyeGaze = new EyeGazeJNI();
		  int result = eyeGaze.EyeGazeInit(0);
		  System.out.print("start device....." + result +"\n'");
		  
		  System.out.print("start calibrating device.....\n");
		  eyeGaze.Calibrate();
		  
		  int startSta = eyeGaze.EyeGazeLogStart();
		  if(startSta == 0) {
			  System.out.print("start logging data in trace.dat file.....\n");
		  }else {
			  System.out.print("start logging failed.....\n");
		  }
		  
		  Timer timer = new Timer();
		  timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			    // Your database code here
				  eyeGaze.EyeGazeLogStop();
				  eyeGaze.EyeGazeShutDown();
			  }
			}, 30*1000);
	   }

}
