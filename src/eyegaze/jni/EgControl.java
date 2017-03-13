package eyegaze.jni;

public class EgControl {
	
	 private boolean isTrackingActive;
	 
	 private int iScreenWidthPix;
	 
	 private int iScreenHeightPix;
	 
	 private int iCommenType;

	public boolean isTrackingActive() {
		return isTrackingActive;
	}

	public void setTrackingActive(boolean isTrackingActive) {
		this.isTrackingActive = isTrackingActive;
	}

	public int getiScreenWidthPix() {
		return iScreenWidthPix;
	}

	public void setiScreenWidthPix(int iScreenWidthPix) {
		this.iScreenWidthPix = iScreenWidthPix;
	}

	public int getiScreenHeightPix() {
		return iScreenHeightPix;
	}

	public void setiScreenHeightPix(int iScreenHeightPix) {
		this.iScreenHeightPix = iScreenHeightPix;
	}

	public int getiCommenType() {
		return iCommenType;
	}

	public void setiCommenType(int iCommenType) {
		this.iCommenType = iCommenType;
	}

}
