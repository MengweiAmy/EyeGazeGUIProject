package eyegaze.gui.model;

public class Sample {
	
	public Sample(long l, String s)
	{
		time = l;
		key = s;
	}

	public String toString()
	{
		// first change SPACE to underscore
		if (key.equals(" "))
			key = "_";
		return key + ", " + time;
	}

	private long time;
	private String key;

}
