package eyegaze.gui.model;

public class Sample {
	
	public Sample(long l, float sec, String s)
	{
		time = l;
		key = s;
		second = sec;
	}
	
	public void setXPos(int x) {
		xPos = x;
	}
	
	public void setYPos(int y) {
		yPos = y;
	}
	
	public int getDwellTime() {
		return dwellTime;
	}

	public void setDwellTime(int dwellTime) {
		this.dwellTime = dwellTime;
	}

	public String toString()
	{
		// first change SPACE to underscore
		if (key.equals(" "))
			key = "_";
		//return key + ", " + time  + "," + second + "," + xPos + "," + yPos;
		return  "       "+ key + "         " + time  + "         " + second + "        " + xPos + "        " + yPos + "         " + dwellTime;
	}

	private long time;
	private String key;
	private float second;
	private int xPos;
	private int yPos;
	
	private int dwellTime;

}
