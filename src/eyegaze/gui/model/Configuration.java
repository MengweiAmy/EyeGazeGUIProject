package eyegaze.gui.model;

public class Configuration {
	
	private int controlType;
	
	private int fixationSamples;
	
	private int fixationOffset;
	
	private int dwellTime;

	public int getControlType() {
		return controlType;
	}

	public void setControlType(int controlType) {
		this.controlType = controlType;
	}

	public int getFixationSamples() {
		return fixationSamples;
	}

	public void setFixationSamples(int fixationSamples) {
		this.fixationSamples = fixationSamples;
	}

	public int getFixationOffset() {
		return fixationOffset;
	}

	public void setFixationOffset(int fixationOffset) {
		this.fixationOffset = fixationOffset;
	}

	public int getDwellTime() {
		return dwellTime;
	}

	public void setDwellTime(int dwellTime) {
		this.dwellTime = dwellTime;
	}
	
	

}
