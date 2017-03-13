package eg.application;

public class Test {
	
	   public static void main(String[] arg0s) {
//		  EyeGazeJNI eyeGaze = new EyeGazeJNI();
//		  int result = eyeGaze.EyeGazeInit(0);
//		  System.out.print("start device....." + result +"\n'");
//		  
//		  System.out.print("start calibrating device.....\n");
//		  eyeGaze.Calibrate();
		  
//		  new Thread() {
//			  public void run() {
//				  EyeGazeData data = eyeGaze.getEyeGazeData();
//				  if(data != null) {
//					  System.out.print("PupilRadiusMm....."+data.getPupilRadiusMm()+"\n");
//					  System.out.print("fXEyeballOffsetMm....."+data.getfXEyeballOffsetMm()+"\n");
//					  System.out.print("fyEyeballOffsetMm....."+data.getfYEyeballOffsetMm()+"\n");
//				  }else {
//					  System.out.print("data is null.....\n");
//				  }
//			  }
//		  }.start();

//		  int startSta = eyeGaze.EyeGazeLogStart();
//		  if(startSta == 0) {
//			  System.out.print("start logging data in trace.dat file....."+startSta+"\n");
//		  }else {
//			  System.out.print("start logging failed....."+startSta+"\n");
//		  }
//		  
//		  Timer timer = new Timer();
//		  timer.schedule(new TimerTask() {
//			  @Override
//			  public void run() {
//			    // Your database code here
//				  //eyeGaze.EyeGazeLogStop();
//				  eyeGaze.EyeGazeShutDown();
//			  }
//			}, 30*1000);
		   SoftKeyboardExperiment soft = new SoftKeyboardExperiment();
	   }

}
