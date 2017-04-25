package eyegaze.gui.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import eyegaze.gui.model.Configuration;
import eyegaze.jni.EyeGazeData;

public class WriteFinalGazeLog {
	
	private static WriteFinalGazeLog write;

	BufferedWriter CfgWrite;
	BufferedReader CfgRead;
	String LineRead, TmpRead, Appender;

	public static WriteFinalGazeLog getInstance() {
		if (write == null) {
			write = new WriteFinalGazeLog();
		}
		return write;
	}

	// Write the click button info into the dat file
	public void CfgWriter(List<EyeGazeData> data, String FileToWriteTo) {
		Configuration config = ConfigurationService.getInstance().loadConfig();
		try {
			CfgWrite = new BufferedWriter(new FileWriter(FileToWriteTo, true));
			CfgWrite.newLine();
			CfgWrite.write("Index    blockNo     dwellTime     ControlType      PupilSize");
			CfgWrite.newLine();
			for (int i = 0; i < data.size(); i++) {
				CfgWrite.write(i+"   " + "     "  + config.getDwellTime() + config.getControlType() + "     " 
			+ data.get(i).getPupilRadiusMm() + "    ");
				CfgWrite.newLine();
			}
			CfgWrite.close();
		} catch (IOException e) {
			System.out.println("Problem!: " + e);
		}
	}

}
