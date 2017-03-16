package eyegaze.gui;

import java.util.List;

import eyegaze.gui.control.AnalysisGazeLog;
import eyegaze.gui.control.MouseControlService;
import eyegaze.jni.EyeGazeData;

public class Tes {
	
	public static void main(String[] arg0s) {
		List<EyeGazeData> datalist = AnalysisGazeLog.getInstance().getEyeGazeData();
		MouseControlService serv = new MouseControlService();
		serv.verifyFixtionData(datalist);
	}

}
