package data;

public class EyeGazeData {
	
	private boolean gazeVectorFound;
	
	private int iIGaze;
	
	private int iJGaze;
	
	private float pupilRadiusMm;
	
	private float fXEyeballOffsetMm;
	
	private float fYEyeballOffsetMm;
	
	private float focusRangeImageTime;
	
	private float foucsRangeOffsetMm;
	
	private float fLengExtOffsetMm;
	
	private long cameraFieldCount;
	
	private double gazeTimeSec;
	
	private double appMarkTimeSec;
	
	private int appMarkCount;
	
	private double reportTimeSec;

	public boolean isGazeVectorFound() {
		return gazeVectorFound;
	}

	public void setGazeVectorFound(boolean gazeVectorFound) {
		this.gazeVectorFound = gazeVectorFound;
	}

	public int getiIGaze() {
		return iIGaze;
	}

	public void setiIGaze(int iIGaze) {
		this.iIGaze = iIGaze;
	}

	public int getiJGaze() {
		return iJGaze;
	}

	public void setiJGaze(int iJGaze) {
		this.iJGaze = iJGaze;
	}

	public float getPupilRadiusMm() {
		return pupilRadiusMm;
	}

	public void setPupilRadiusMm(float pupilRadiusMm) {
		this.pupilRadiusMm = pupilRadiusMm;
	}

	public float getfXEyeballOffsetMm() {
		return fXEyeballOffsetMm;
	}

	public void setfXEyeballOffsetMm(float fXEyeballOffsetMm) {
		this.fXEyeballOffsetMm = fXEyeballOffsetMm;
	}

	public float getfYEyeballOffsetMm() {
		return fYEyeballOffsetMm;
	}

	public void setfYEyeballOffsetMm(float fYEyeballOffsetMm) {
		this.fYEyeballOffsetMm = fYEyeballOffsetMm;
	}

	public float getFocusRangeImageTime() {
		return focusRangeImageTime;
	}

	public void setFocusRangeImageTime(float focusRangeImageTime) {
		this.focusRangeImageTime = focusRangeImageTime;
	}

	public float getFoucsRangeOffsetMm() {
		return foucsRangeOffsetMm;
	}

	public void setFoucsRangeOffsetMm(float foucsRangeOffsetMm) {
		this.foucsRangeOffsetMm = foucsRangeOffsetMm;
	}

	public float getfLengExtOffsetMm() {
		return fLengExtOffsetMm;
	}

	public void setfLengExtOffsetMm(float fLengExtOffsetMm) {
		this.fLengExtOffsetMm = fLengExtOffsetMm;
	}

	public long getCameraFieldCount() {
		return cameraFieldCount;
	}

	public void setCameraFieldCount(long cameraFieldCount) {
		this.cameraFieldCount = cameraFieldCount;
	}

	public double getGazeTimeSec() {
		return gazeTimeSec;
	}

	public void setGazeTimeSec(double gazeTimeSec) {
		this.gazeTimeSec = gazeTimeSec;
	}

	public double getAppMarkTimeSec() {
		return appMarkTimeSec;
	}

	public void setAppMarkTimeSec(double appMarkTimeSec) {
		this.appMarkTimeSec = appMarkTimeSec;
	}

	public int getAppMarkCount() {
		return appMarkCount;
	}

	public void setAppMarkCount(int appMarkCount) {
		this.appMarkCount = appMarkCount;
	}

	public double getReportTimeSec() {
		return reportTimeSec;
	}

	public void setReportTimeSec(double reportTimeSec) {
		this.reportTimeSec = reportTimeSec;
	}

}
