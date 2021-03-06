package eyegaze.gui.model;

public class Configuration {
	
	private int controlType;
	
	private int fixationSamples;
	
	private int fixationOffset;
	
	private int dwellTime;
	
	private int blockRef;

	private int dwellType;
	
	private int sentenceType;
	
	private int blockSize;
	
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

	public int getBlockRef() {
		return blockRef;
	}

	public void setBlockRef(int blockRef) {
		this.blockRef = blockRef;
	}

	public int getDwellType() {
		return dwellType;
	}

	public void setDwellType(int dwellType) {
		this.dwellType = dwellType;
	}

	public int getSentenceType() {
		return sentenceType;
	}

	public void setSentenceType(int sentenceType) {
		this.sentenceType = sentenceType;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

}
