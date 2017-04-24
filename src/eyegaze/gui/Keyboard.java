package eyegaze.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JProgressBar;

import eyegaze.gui.model.KeyBt;

/**
 * Create button with progressbar display on it
 * It should be actived in gaze control
 * @author EYEGAZE 2.3 i7
 *
 */
public class Keyboard extends JLayeredPane implements ActionListener {
	
	// the following avoids a "warning" with Java 1.5.0 complier (?)
	static final long serialVersionUID = 42L;
	final Font DEFAULT_KEY_FONT = new Font("sansserif", Font.BOLD, 20);

	JButton[] keyButton;
	JProgressBar[] progressBar;
	Map<KeyBt,JButton> keyButtonMap;

	public Keyboard(KeyBt[] keyArg, ActionListener alArg, String controlType)
	{      
		KeyBt[] key = keyArg;
		keyButton = new JButton[keyArg.length];
		progressBar = new JProgressBar[keyArg.length];
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
			add(keyButton[i], new Integer(2));
			
			/*
			 * If gaze control mode, add progress bar on each button
			 */
			if(controlType.equals("Gaze Control")) {
				progressBar[i] = createProgressBar();
				int widRad = 30;
				int centerWid = key[i].getW()/2;
				int centerHei = key[i].getH()/2;
				progressBar[i].setBounds(key[i].getX()+(centerWid-widRad), key[i].getY()+(centerHei-widRad), widRad*2, widRad*2);
				//progressBar[i].setBounds(key[i].getX(), key[i].getY(), key[i].getW(), key[i].getH());
	            add(progressBar[i] , new Integer(1));
			}
		}
		
		this.setLayout(null);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
	}
	
	/**
	 * Create a progress bar
	 * @return
	 */
	private JProgressBar createProgressBar() {
	    JProgressBar progress = new JProgressBar();
	    // use JProgressBar#setUI(...) method
	    progress.setUI(new ProgressCircleUI(true));
	    progress.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
	    progress.setStringPainted(true);
	    progress.setFont(progress.getFont().deriveFont(18f));
	    progress.setForeground(Color.ORANGE);
	    
	    return progress;
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
	
	public JProgressBar[] getProgressBarList() {
		return progressBar;
	}

}
