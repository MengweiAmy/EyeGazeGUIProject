package eyegaze.gui.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eyegaze.jni.EyeGazeData;

public class AnalysisGazeLog {
	
	static List<EyeGazeData> data = new ArrayList<EyeGazeData>();
	
	static boolean isStartAnalysi = false;
	
	private static void loadFile() {
		try {
			FileReader file = new FileReader(new File("trace.dat"));
			BufferedReader br = new BufferedReader(file);
			String temp = br.readLine();
			while (temp != null) {
				temp = br.readLine();
				analysisGazeData(temp);
				System.out.println(temp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void analysisGazeData(String temp) {
		//replaceAll("\\s{2,}", " ").trim();
		if(temp != null) {
			temp = temp.replaceAll("\\s{2,}", " ").trim();
			if(temp != "" && temp.length() > 0) {
				String[] valus = temp.split(" ");
				if(valus != null && valus.length >0) {
					if(valus[0].equals("0") && !isStartAnalysi){
						isStartAnalysi = true;
					}
					if(isStartAnalysi) {
						EyeGazeData newD = new EyeGazeData();
						newD.setGazeVectorFound(Integer.valueOf(valus[1]));
						newD.setiIGaze(Integer.valueOf(valus[2]));
						newD.setiJGaze(Integer.valueOf(valus[3]));
						newD.setPupilRadiusMm(Float.valueOf(valus[4]));
						newD.setFoucsRangeOffsetMm(Float.valueOf(valus[8]));
						newD.setGazeTimeSec(Double.valueOf(valus[11]));
						newD.setReportTimeSec(Double.valueOf(valus[12]));
						data.add(newD);
					}
				}
			}
		}
	}
	
	public List<EyeGazeData> getEyeGazeData() {
		loadFile();
		return data;
	}
	
	public static void main(String[] args) {
		loadFile();
	}

}
