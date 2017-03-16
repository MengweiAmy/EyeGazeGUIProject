package eyegaze.gui;

import java.util.List;

import eyegaze.gui.control.AnalysisGazeLog;
import eyegaze.jni.FixtionData;

public class Tes {
	
	public static void main(String[] arg0s) {
		List<FixtionData> data = AnalysisGazeLog.getInstance().getFixationData();
		
		SoftKeyBoardMain main = new SoftKeyBoardMain("0");
		main.createAndShowGUI();
		
		if(data != null) {
			for (int i=0;i<data.size();i++) {
				int x = (int)data.get(i).getxFixDelayed();
				int y = (int)data.get(i).getyFixDelayed();
				if(checkIfLookedText(x,y)) {
					System.out.println("User is looking at text area");
				}else {
					String label = main.getKeyByPosition(x, y);
					System.out.println(label);
				}
			}
		}		
	}
	
	public static boolean checkIfLookedText(int x,int y) {
		if(y<268) {
			return true;
		}else {
			return false;
		}
	}

}
