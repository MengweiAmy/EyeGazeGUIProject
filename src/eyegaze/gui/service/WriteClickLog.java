package eyegaze.gui.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import eyegaze.gui.model.Sample;

public class WriteClickLog {

	private static WriteClickLog write;

	BufferedWriter CfgWrite;
	BufferedReader CfgRead;
	String LineRead, TmpRead, Appender;

	public static WriteClickLog getInstance() {
		if (write == null) {
			write = new WriteClickLog();
		}
		return write;
	}

	// Write the click button info into the dat file
	public void CfgWriter(String target, String present, Vector<Sample> samples, String FileToWriteTo) {
		try {
			CfgWrite = new BufferedWriter(new FileWriter(FileToWriteTo, false));
			
			CfgWrite.write("Presented Phrase: " + present);
			CfgWrite.newLine();
			CfgWrite.write("Entered Phrase: " + target);
			CfgWrite.newLine();
			CfgWrite.write("Index    Letter       Time       Seconds      Xpos       YPos    DWellTime ");
			CfgWrite.newLine();
			for (int i = 0; i < samples.size(); i++) {
				CfgWrite.write(i+"   " + samples.get(i).toString());
				CfgWrite.newLine();
			}
			CfgWrite.close();
		} catch (IOException e) {
			System.out.println("Problem!: " + e);
		}
	}
}
