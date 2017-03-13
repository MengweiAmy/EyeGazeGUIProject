package eyegaze.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import eyegaze.gui.model.KeyBt;

public class Keyboard extends JPanel implements ActionListener {
	
	// the following avoids a "warning" with Java 1.5.0 complier (?)
	static final long serialVersionUID = 42L;
	final Font DEFAULT_KEY_FONT = new Font("sansserif", Font.BOLD, 16);

	JButton[] keyButton;
	int mode;
	Map<KeyBt,JButton> keyButtonMap;

	public Keyboard(KeyBt[] keyArg, ActionListener alArg, int modeArg)
	{
		KeyBt[] key = keyArg;
		keyButton = new JButton[keyArg.length];
		mode = modeArg;
		keyButtonMap = new HashMap<KeyBt, JButton>();

		// determine required dimension for panel
		int width = 0;
		int height = 0;
		for (int i = 0; i < key.length; ++i)
		{
			int wTemp = key[i].getX() + key[i].getW();
			width = wTemp > width ? wTemp : width;

			int hTemp = key[i].getY() + key[i].getH();
			height = hTemp > height ? hTemp : height;
		}
		Dimension d = new Dimension(width, height);

		for (int i = 0; i < keyButton.length; ++i)
		{
			keyButton[i] = new JButton(key[i].getLabel());
			keyButton[i].setBackground(Color.LIGHT_GRAY);
			keyButton[i].setFont(DEFAULT_KEY_FONT);
			keyButton[i].addActionListener(alArg);
			keyButton[i].setBounds(key[i].getX(), key[i].getY(), key[i].getW(), key[i].getH());
			keyButtonMap.put(key[i], keyButton[i]);
			this.add(keyButton[i]);
		}
		this.setLayout(null);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
	}

	ActionEvent ae;

	public ActionEvent getActionEvent()
	{
		return ae;
	}

	public void setActionEvent(ActionEvent aeArg)
	{
		ae = aeArg;
	}

	public void actionPerformed(ActionEvent ae)
	{
		setActionEvent(ae);
	}
	
	public JButton[] getJButtonList() {
		return keyButton;
	}

}
