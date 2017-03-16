package eyegaze.gui.control;

import java.util.List;

import eyegaze.jni.EyeGazeData;
import eyegaze.jni.EyeGazeJNI;

public class MouseControlService {
	
	public void verifyFixtionData(List<EyeGazeData> datalist) {
		if(datalist != null && datalist.size() >0) {
			EyeGazeData[] dataArray = new EyeGazeData[datalist.size()];
			for(int i=0;i<datalist.size();i++) {
				dataArray[i] = datalist.get(i);
			}
			int result = EyeGazeJNI.getInstance().VerifyFixation(dataArray);
			System.out.println("GET DIXATION LOGGED DATA"+result);
		}
	}
}
