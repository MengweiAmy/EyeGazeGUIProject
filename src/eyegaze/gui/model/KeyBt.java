package eyegaze.gui.model;

public class KeyBt {
	
	String label;
	int x, y, w, h; // x- and y-position, width, height

	public KeyBt(String labelArg, int xArg, int yArg, int wArg, int hArg)
	{
		label = labelArg;
		x = xArg;
		y = yArg;
		w = wArg;
		h = hArg;
	}

	public String getLabel()
	{
		if (label.equals("Space"))
			return " ";
		else
			return label;
	};

	public int getX()
	{
		return x;
	};

	public int getY()
	{
		return y;
	};

	public int getW()
	{
		return w;
	};

	public int getH()
	{
		return h;
	};

}
