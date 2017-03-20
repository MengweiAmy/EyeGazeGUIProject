package eyegaze.gui.model;

public class FixationModel {
	
	/*
	 * Could be set from application later
	 */
	private static int offset = 65;
	
	private int fixationIndex;
	
	private int isEyeVectorFound;
	
	private int xPosition;
	
	private int yPosition;
	
	private float pupilDiam;
	
	private int iSaccadeDurationDelayed;
	
	private int iFixDurationDelayed;

	public int getFixationIndex() {
		return fixationIndex;
	}

	public void setFixationIndex(int fixationIndex) {
		this.fixationIndex = fixationIndex;
	}

	public int getIsEyeVectorFound() {
		return isEyeVectorFound;
	}

	public void setIsEyeVectorFound(int isEyeVectorFound) {
		this.isEyeVectorFound = isEyeVectorFound;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public float getPupilDiam() {
		return pupilDiam;
	}

	public void setPupilDiam(float pupilDiam) {
		this.pupilDiam = pupilDiam;
	}

	public int getiSaccadeDurationDelayed() {
		return iSaccadeDurationDelayed;
	}

	public void setiSaccadeDurationDelayed(int iSaccadeDurationDelayed) {
		this.iSaccadeDurationDelayed = iSaccadeDurationDelayed;
	}

	public int getiFixDurationDelayed() {
		return iFixDurationDelayed;
	}

	public void setiFixDurationDelayed(int iFixDurationDelayed) {
		this.iFixDurationDelayed = iFixDurationDelayed;
	}
	
	/*
	 * Verify if it is the same fixation
	 */
	public boolean isSameFixation(int xPos, int yPos) {
		boolean isXSameFix = false;
		boolean isYSameFix = false;
		if(xPos > xPosition-offset && xPos < xPosition + offset) {
			isXSameFix = true;
		}
		
		if(yPos > yPosition-offset && yPos < yPosition + offset) {
			isYSameFix = true;
		}
		
		if(isXSameFix && isYSameFix) {
			return true;
		}
		return false;
	}
}
