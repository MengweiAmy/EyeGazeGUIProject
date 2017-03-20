package eyegaze.jni;

public class FixationIndex {
	
	/*
	 * Current fixation index
	 */
	private int fixationIndex;
	
	/*
	 * Average x position
	 */
	private int xPos;
	
	
	/*
	 * Average y position
	 */
	private int yPos;
	
	/*
	 * 
	 */
	private int saccadeDuration;
	
	/*
	 * Fixation duration, larger than the minimum fixation occurs would be counted as one fixation
	 * Minimum fixation occurs is 3
	 */
	private int fixationDuration;
	
	/*
	 * Start index from the raw fixation data
	 */
	private int fixationStartIndex;

	public int getFixationIndex() {
		return fixationIndex;
	}

	public void setFixationIndex(int fixationIndex) {
		this.fixationIndex = fixationIndex;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getSaccadeDuration() {
		return saccadeDuration;
	}

	public void setSaccadeDuration(int saccadeDuration) {
		this.saccadeDuration = saccadeDuration;
	}

	public int getFixationDuration() {
		return fixationDuration;
	}

	public void setFixationDuration(int fixationDuration) {
		this.fixationDuration = fixationDuration;
	}

	public int getFixationStartIndex() {
		return fixationStartIndex;
	}

	public void setFixationStartIndex(int fixationStartIndex) {
		this.fixationStartIndex = fixationStartIndex;
	}
}
