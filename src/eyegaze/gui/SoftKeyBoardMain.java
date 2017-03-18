package eyegaze.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import eyegaze.device.EyeDeviceControl;
import eyegaze.gui.control.AnalysisGazeLog;
import eyegaze.gui.control.MouseControlService;
import eyegaze.gui.model.KeyBt;
import eyegaze.gui.model.Sample;

public class SoftKeyBoardMain extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	
	private Font BIG = new Font("monospaced", Font.BOLD, 24);
	private Color BACKGROUND = new Color(254, 254, 218);
	private Color FOREGROUND = new Color(11, 11, 109);
	
	KeyBt[] keyboardSet;
	
	private JPanel p1;
	JButton[] jbtnList;
	
	private JTextField text1;
	private JTextField text2;
	
	private BufferedWriter sd1File;
	
	private String[] phrases;
	private String presentedPhrase; // presented phrase
	private String targetPhrase; // presented phrase
	
	private Vector<Sample> samples= new Vector<Sample>();;
	
	private long t1, t2 , t3;
	private int count; // count keystrokes per phrase
	private Random r = new Random();//generate random index to display random sentence from phrase file
	
	private boolean isLogstared;
	private boolean isDeviceStarted = false;
	private String controlType;
	
	public SoftKeyBoardMain(String controlType) {
		this.controlType = controlType;
	}

    public void createAndShowGUI() {
    	
    	//Start initial device and calibrate process
    	
    	/*
    	 * Every time comment these two lines if don not need device when debugging
    	 */
    	
    	//EyeDeviceControl.getInstance().initializeDevice();
    	//isDeviceStarted = true;
    	
	    JFrame frame = this;
	    frame.setTitle("Current Control Type:" + controlType);
	    p1 = createTextField();
	    
	    JPanel glass = new JPanel(new GridLayout(0, 1));
        // trap both mouse and key events.  Could provide a smarter 
        // key handler if you wanted to allow things like a keystroke 
        // that would cancel the long-running operation.
        glass.addMouseListener(new MouseAdapter() {});
        glass.addMouseMotionListener(new MouseMotionAdapter() {});
        glass.addKeyListener(new KeyAdapter() {});
        
        /*
         * Make the keyboard visible
         */
        glass.setOpaque(false);
        // make sure the focus won't leave the glass pane
        // glass.setFocusCycleRoot(true);  // 1.4
        //padding.setNextFocusableComponent(padding);  // 1.3
        setGlassPane(glass);
        if(controlType == "Gaze Control"){
        	glass.setVisible(true);
        	//try button click function
        	//getKeyByPosition(400,400);
        }
        
	    this.setContentPane(p1);
	    WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(frame,
                        "Are You Sure to Close this Application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
                	if(isDeviceStarted) {
                    	EyeDeviceControl.getInstance().stopLogging();
                    	EyeDeviceControl.getInstance().shutdonwDevice();
                	}
                	MouseControlService service = new MouseControlService();
        			service.verifyFixtionData(AnalysisGazeLog.getInstance().getEyeGazeData());
                	System.exit(0);
                }
            }
        };
        this.addWindowListener(exitListener);
	    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setVisible(true);
    }
    
    public JPanel createTextField() {
    	
    	//Load phrases library
		loadPhrases();

		//Initialize components
		text1 = new JTextField(70);
		text1.setFocusable(false);
		text1.setFont(BIG);
		text1.setBackground(new Color(254, 254, 218));
		text1.setForeground(new Color(11, 11, 109));
		text1.setPreferredSize(new Dimension(120,50));
		JPanel text1Panel = new JPanel();
		text1Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		text1Panel.add(text1);
		text1Panel.setBorder(new TitledBorder(new EtchedBorder(), "Presented text"));
		
		text2 = new JTextField(70);
		text2.setEditable(true); // allows I-beam to show
		text2.setFont(BIG);
		text2.setBackground(BACKGROUND);
		text2.setForeground(FOREGROUND);
		text2.setPreferredSize(new Dimension(120,60));
		JPanel text2Panel = new JPanel();
		text2Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		text2Panel.add(text2);
		text2Panel.setBorder(new TitledBorder(new EtchedBorder(), "Transcribed text"));
		
		text1.setText(presentedPhrase);
		targetPhrase = "";
		text2.setText(targetPhrase);
		
		// layout components
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.add(Box.createRigidArea(new Dimension(100, 30)));
		p1.add(text1Panel);
		p1.add(Box.createRigidArea(new Dimension(100, 30)));
		p1.add(text2Panel);
		p1.add(Box.createRigidArea(new Dimension(100, 30)));
		
		JPanel keyboardPanel = createKeyboard();
		
		JPanel p = new JPanel(new BorderLayout());
		p.add(p1, "North");
		p.add(keyboardPanel, "Center");
		
		return p;
    }
    
    public JPanel createKeyboard() {
    	
    	//Adjust the key size with different screen size
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	double width = screenSize.getWidth();
    	double height = screenSize.getHeight();
    	System.out.println(width);
    	System.out.println(height);
    	
    	int w = (int) (width/12);
    	System.out.println("w"+w);
    	int h = 144;
    	KeyBt[] qwertyKeyboard = { new KeyBt("Q", 0 * w, 0, w, h), new KeyBt("W", 1 * w, 0, w, h), new KeyBt("E", 2 * w, 0, w, h),
    			new KeyBt("R", 3 * w, 0, w, h), new KeyBt("T", 4 * w, 0, w, h), new KeyBt("Y", 5 * w, 0, w, h),
    			new KeyBt("U", 6 * w, 0, w, h), new KeyBt("I", 7 * w, 0, w, h), new KeyBt("O", 8 * w, 0, w, h),
    			new KeyBt("P", 9 * w, 0, w, h), new KeyBt("A", (w / 3) + 0 * w, h, w, h),
    			new KeyBt("S", (w / 3) + 1 * w, h, w, h), new KeyBt("D", (w / 3) + 2 * w, h, w, h),
    			new KeyBt("F", (w / 3) + 3 * w, h, w, h), new KeyBt("G", (w / 3) + 4 * w, h, w, h),
    			new KeyBt("H", (w / 3) + 5 * w, h, w, h), new KeyBt("J", (w / 3) + 6 * w, h, w, h),
    			new KeyBt("K", (w / 3) + 7 * w, h, w, h), new KeyBt("L", (w / 3) + 8 * w, h, w, h),
    			new KeyBt("Z", (2 * w / 3) + 0 * w, 2 * h, w, h), new KeyBt("X", (2 * w / 3) + 1 * w, 2 * h, w, h),
    			new KeyBt("C", (2 * w / 3) + 2 * w, 2 * h, w, h), new KeyBt("V", (2 * w / 3) + 3 * w, 2 * h, w, h),
    			new KeyBt("B", (2 * w / 3) + 4 * w, 2 * h, w, h), new KeyBt("N", (2 * w / 3) + 5 * w, 2 * h, w, h),
    			new KeyBt("M", (2 * w / 3) + 6 * w, 2 * h, w, h), new KeyBt("Space", 2 * w, 3 * h, 7 * w, h),
    			new KeyBt("Enter", (w / 3) + 9 * w, 1 * h, 2 * w, h), new KeyBt("Bksp", 10 * w, 0, 2 * w, h),
    			new KeyBt("Setting", 0 * w, 4 * h, 12 * w, h), new KeyBt("Shift", (2 * w / 3) + 7 * w, 2 * h, 4 * w, h),};
    	
    	Keyboard keyboard = new Keyboard(qwertyKeyboard, this, 1);
    	keyboardSet = qwertyKeyboard;
    	jbtnList = keyboard.getJButtonList();
    	JPanel keyboardPanel = new JPanel();
		keyboardPanel.add(keyboard);
		keyboardPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		return keyboardPanel;
    }
    
    public void loadPhrases() {
    	// ------------------
    	// initialize phrases
    	// ------------------
    	String phrasesFile = "phrases.txt";
    	Scanner inFile = null;
    	try
    	{
    		inFile = new Scanner(new File(phrasesFile));
    	} catch (FileNotFoundException e)
    	{
    		System.exit(1);
    	}
    			
    	ArrayList<String> arrayList = new ArrayList<String>();
    	while (inFile.hasNextLine()) {
    		arrayList.add(inFile.nextLine());
    		phrases = arrayList.toArray(new String[arrayList.size()]);
    	}
    	
    	// present first phrase for input
    	presentedPhrase = phrases[r.nextInt(phrases.length)];
    }
    
    //Based on the x,y axis to analyse current button
    public String getKeyByPosition(int x, int y) {
    	int realY = y- p1.getComponent(1).getY();
    	for(int i=0;i<keyboardSet.length;i++) {
    		KeyBt key = keyboardSet[i];
    		if(key.getX() < x && x < key.getX() + key.getW()) {
    			if(key.getY() < realY && realY < key.getY() + key.getH()) {
    				System.out.println("jbtn"+jbtnList[i].getText());
    				jbtnList[i].doClick();
    				return key.getLabel();
    			}
    		}
    	}
    	return "";
    }
    
    public void initilizeOutput() {
		
		//Initialize the output files
		String s1 = "";
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss-ddMM-yyyy");
        System.out.println( sdf.format(cal.getTime()) );
		//s1 = sdf.format(cal.getTime())+"-"+controlType+".dat";
		s1 = "ClickInfo"+"-"+controlType+".dat";
		try
		{
			sd1File = new BufferedWriter(new FileWriter(s1));
		} catch (IOException e)
		{
			System.out.println("I/O error: can't open sd1/sd2 data files");
			System.exit(0);
		}

		try
		{
			sd1File.write("");
			sd1File.flush();
		} catch (IOException e)
		{
			System.err.println("ERROR WRITING TO DATA FILE!\n" + e);
			System.exit(1);
		}
    }

	@Override
	public void actionPerformed(ActionEvent action) {
		// TODO Auto-generated method stub
		
		if(!isLogstared && isDeviceStarted) {
			EyeDeviceControl.getInstance().startLogging();
			isLogstared = true;
		}
		
		JButton jb = (JButton)action.getSource();
		String s = jb.getText();
		char c = (s.toLowerCase()).charAt(0);
		System.out.println(s+" is clicked");
		
		int x = jb.getX();
		//Add the offset of jPanel. The raw Y axis is the position based on keyboard panel
		//Should add the height of text area, then it is the real position of the button
		int y = jb.getY()+p1.getComponent(1).getY();
		
		++count;
		if (t1 == 0)
			t1 = System.currentTimeMillis();
		t2 = System.currentTimeMillis() - t1;
		
		/*
		 * Calculate the click time based on the device init time(sec)
		 */
		t3 = System.currentTimeMillis() - EyeDeviceControl.getInstance().getDeviceInitTime();
		Sample newSam = new Sample(t2, (float)(t3/1000.0), s.toLowerCase());
		newSam.setXPos(x);
		newSam.setYPos(y);
		samples.addElement(newSam);

		if (s.equals("Enter")){
			initilizeOutput();
			//TODO Finish current input and write the log
			//Stop logging the eyegaze data if the eye tracker is started
			String s2 = text2.getText().toLowerCase();
			
			// build output data for sd1 file
			StringBuilder sd1Stuff = new StringBuilder();
			//sd1Stuff.append(presentedPhrase).append('\n');
			//sd1Stuff.append(s2).append('\n');
			sd1Stuff.append("Letter    Time    Seconds    Xpos     YPos  \n");
			for (int i = 0; i < samples.size(); ++i)
				sd1Stuff.append(samples.elementAt(i)).append('\n');
			sd1Stuff.append('#').append('\n');
			
			// dump data
			try
			{
				sd1File.write(sd1Stuff.toString(), 0, sd1Stuff.length());
				sd1File.flush();
			} catch (IOException e)
			{
				System.err.println("ERROR WRITING TO DATA FILE!\n" + e);
				System.exit(1);
			}

			JLabel thankyou = new JLabel("End of block. Thank you.");
			thankyou.setFont(new Font("sansserif", Font.PLAIN, 16));
			JOptionPane.showMessageDialog(this, thankyou);
			try
			{
				sd1File.close();
			} catch (IOException e)
			{
				System.err.println("ERROR CLOSING DATA FILES!\n" + e);
				System.exit(1);
			}

			// prepare for next phrase
		    samples = new Vector<Sample>();
			presentedPhrase = phrases[r.nextInt(phrases.length)];
			text1.setText(presentedPhrase);
			targetPhrase = "";
			text2.setText(targetPhrase);
			t1 = 0;
			count = 0;
		}else if (s.equals("Bksp"))
		{
			if (targetPhrase.length() >= 1)
			{
				targetPhrase = targetPhrase.substring(0, targetPhrase.length() - 1);
				text2.setText(targetPhrase);
				text2.requestFocus(); // so I-beam (caret) does not disappear
			}
		}else if(s.equals("Setting")) {
			//TODO
		}else if(s.equals("Shift")) {
			//TODO
		}
		else
		// just a keystroke; add to transcribed text
		{
			targetPhrase += c;
			text2.setText(targetPhrase);
			text2.requestFocus(); // so I-beam (caret) does not disappear
		}
		
	}
	
	public JPanel getMainPanel() {
		return p1;
	}
    
}
