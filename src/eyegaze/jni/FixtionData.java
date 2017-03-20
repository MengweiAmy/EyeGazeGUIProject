package eyegaze.jni;

public class FixtionData {
	
	private int gazePointFoundDelayed;
	
	private float xGazeDelayed;
	
	private float yGazeDelayed;
	
	private float gazeDeviationDelayed;
	
	private float xFixDelayed;
	
	private float yFixDelayed;
	
	private int iSaccadeDurationDelayed;
	
	private int iFixDurationDelayed;
	
	private int iFixtionIndex;

	public int getGazePointFoundDelayed() {
		return gazePointFoundDelayed;
	}

	public void setGazePointFoundDelayed(int gazePointFoundDelayed) {
		this.gazePointFoundDelayed = gazePointFoundDelayed;
	}

	public float getxGazeDelayed() {
		return xGazeDelayed;
	}

	public void setxGazeDelayed(float xGazeDelayed) {
		this.xGazeDelayed = xGazeDelayed;
	}

	public float getyGazeDelayed() {
		return yGazeDelayed;
	}

	public void setyGazeDelayed(float yGazeDelayed) {
		this.yGazeDelayed = yGazeDelayed;
	}

	public float getGazeDeviationDelayed() {
		return gazeDeviationDelayed;
	}

	public void setGazeDeviationDelayed(float gazeDeviationDelayed) {
		this.gazeDeviationDelayed = gazeDeviationDelayed;
	}

	public float getxFixDelayed() {
		return xFixDelayed;
	}

	public void setxFixDelayed(float xFixDelayed) {
		this.xFixDelayed = xFixDelayed;
	}

	public float getyFixDelayed() {
		return yFixDelayed;
	}

	public void setyFixDelayed(float yFixDelayed) {
		this.yFixDelayed = yFixDelayed;
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

	public int getiFixtionIndex() {
		return iFixtionIndex;
	}

	public void setiFixtionIndex(int iFixtionIndex) {
		this.iFixtionIndex = iFixtionIndex;
	}
	
}
