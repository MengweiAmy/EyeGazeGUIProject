package eyegaze.gui.control;

import java.util.ArrayList;
import java.util.List;

import eyegaze.jni.EyeGazeData;

public class AnalysisGazeLog {
	
	List<EyeGazeData> data = new ArrayList<EyeGazeData>();
	
	private void loadFile() {
		
	}
	
	private void analysisGazeData() {
		//replaceAll("\\s{2,}", " ").trim();
	}
	
	public List<EyeGazeData> getEyeGazeData() {
		return data;
	}

}
