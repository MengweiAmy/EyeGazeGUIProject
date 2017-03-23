package eyegaze.gui.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eyegaze.jni.EyeGazeData;
import eyegaze.jni.FixtionData;

public class AnalysisGazeLog {

	private static AnalysisGazeLog analysis;

	public static AnalysisGazeLog getInstance() {
		if (analysis == null) {
			analysis = new AnalysisGazeLog();
		}
		return analysis;
	}

	List<EyeGazeData> data = new ArrayList<EyeGazeData>();
	
	List<FixtionData> fixationDatas = new ArrayList<FixtionData>();

	boolean isRawAnaStarted = false;
	
	boolean isFixationAnaStarted = false;
	
	boolean isFixaRealData = false;
	int count =0;

	/**
	 * ************************Analyze Raw gaze point data***************************************
	 */
	private void loadFile() {
		try {
			FileReader file = new FileReader(new File("trace.dat"));
			BufferedReader br = new BufferedReader(file);
			String temp = br.readLine();
			while (temp != null) {
				temp = br.readLine();
				analysisGazeData(temp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Analysis each line
	 * if it starts with 0, then start to handle the data
	 *    otherwise it is one of the header lines
	 */
	private void analysisGazeData(String temp) {
		// replaceAll("\\s{2,}", " ").trim();
		String out = temp;
		if (temp != null) {
			temp = temp.replaceAll("\\s{2,}", " ").trim();
			if (temp != "" && temp.length() > 0) {
				String[] valus = temp.split(" ");
				if (valus != null && valus.length > 0) {
					if (valus[0].equals("0") && !isRawAnaStarted) {
						isRawAnaStarted = true;
					}
					if (isRawAnaStarted) {
						if(count == 0) {
							addNewGazeData(valus);
						}else if(Integer.valueOf(valus[0]) != data.get(count-1).getAppMarkCount()) {
							addNewGazeData(valus);
						}
					}
				}
			}
		}
	}

	/*
	 * Create new EyeGaze data object and add to the list
	 * At same time count plus 1, in order to handler two same lines
	 */
	private void addNewGazeData(String[] valus) {
		EyeGazeData newD = new EyeGazeData();
		newD.setAppMarkCount(Integer.valueOf(valus[0]));
		newD.setGazeVectorFound(Integer.valueOf(valus[1]));
		newD.setiIGaze(Integer.valueOf(valus[2]));
		newD.setiJGaze(Integer.valueOf(valus[3]));
		newD.setPupilRadiusMm(Float.valueOf(valus[4]));
		newD.setfXEyeballOffsetMm(Float.valueOf(valus[5]));
		newD.setfYEyeballOffsetMm(Float.valueOf(valus[6]));
		newD.setfLengExtOffsetMm(Float.valueOf(valus[7]));
		newD.setFoucsRangeOffsetMm(Float.valueOf(valus[8]));
		newD.setGazeTimeSec(Double.valueOf(valus[11]));
		newD.setReportTimeSec(Double.valueOf(valus[12]));
		count++;
		data.add(newD);
	}
	
	/**
	 * Return the analyzed eye gaze data
	 * @return
	 */
	public List<EyeGazeData> getEyeGazeData() {
		loadFile();
		return data;
	}
	
	/**
	 * ************************Analyze Fixation data***************************************
	 */
	
	public void loadFixationFile() {
		try {
			FileReader file = new FileReader(new File("fixation.dat"));
			BufferedReader br = new BufferedReader(file);
			String temp = br.readLine();
			while (temp != null) {
				temp = br.readLine();
				analysisFixationData(temp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void analysisFixationData(String temp) {
		String out = temp;
		if (temp != null) {
			temp = temp.replaceAll("\\s{2,}", " ").trim();
			if (temp != "" && temp.length() > 0) {
				String[] valus = temp.split(" ");
				if (valus != null && valus.length > 0) {
					if(valus[0].equals("fix") && !isFixationAnaStarted) {
						isFixationAnaStarted = true;
					}
					if(isFixationAnaStarted && valus[0].equals("0")) {
						isFixaRealData = true;
					}
					if(isFixationAnaStarted && isFixaRealData) {
						FixtionData fix = new FixtionData();
						fix.setiFixtionIndex(Integer.valueOf(valus[0]));
						fix.setxFixDelayed(Float.valueOf(valus[1]));
						fix.setyFixDelayed(Float.valueOf(valus[2]));
						fix.setiSaccadeDurationDelayed(Integer.valueOf(valus[3]));
						fix.setiFixDurationDelayed(Integer.valueOf(valus[4]));
						fix.setGazeDeviationDelayed(Float.valueOf(valus[5]));
						fixationDatas.add(fix);
					}
				}
			}
		}
	}
	
	/**
	 * Return the analyzed eye gaze data
	 * @return
	 */
	public List<FixtionData> getFixationData() {
		loadFixationFile();
		return fixationDatas;
	}

}
