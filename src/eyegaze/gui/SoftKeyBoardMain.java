package eyegaze.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import eyegaze.device.EyeDeviceControl;
import eyegaze.device.RetrieveDataThread;
import eyegaze.gui.model.KeyBt;
import eyegaze.gui.model.Sample;
import eyegaze.gui.service.AnalysisGazeLog;
import eyegaze.gui.service.MouseControlService;
import eyegaze.gui.service.WriteClickLog;
import eyegaze.jni.EyeGazeRetrieveData;

/**
 * If using pure application mode, Please comment three lines of code
 * 
 * 1. EyeDeviceControl.getInstance().initializeDevice();
      isDeviceStarted = true;
      in the beginning of the createAndShowGUI() function
      
   2. startGazeControl(); in the end of createAndShowGUI() function
 * @author EYEGAZE 2.3 i7
 *
 */
public class SoftKeyBoardMain extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private static SoftKeyBoardMain softKeyboard;
	
	/***********************************************Key board Parameters*********************************/
	private Font BIG = new Font("monospaced", Font.BOLD, 24);
	private Color BACKGROUND = new Color(254, 254, 218);
	private Color FOREGROUND = new Color(11, 11, 109);
	
	KeyBt[] keyboardSet;
	
	private JPanel p1;
	JButton[] jbtnList;
	private JProgressBar[] progressBarList;
	
	private JTextField text1;
	private JTextField text2;
	
	private boolean isShiftPress=false;
	
	/**************************************Load phrases parameters**************************************/
	
	private String[] phrases;
	private String presentedPhrase; // presented phrase
	private String targetPhrase; // presented phrase
	
	private Vector<Sample> samples= new Vector<Sample>();;
	
	private long t1, t2 , t3;
	private int count; // count keystrokes per phrase
	private Random r = new Random();//generate random index to display random sentence from phrase file
	private int finishCount = 0;
	private int sentenceSize;
	
	/*************************************Progress bar parameter*********************************************/
	private int currentIndex=-1;
	private int currentLayer = 3;
	
	Keyboard keyboardLayerPanel;
	
	private Timer timer;
	boolean hasPerformClick;
	boolean isTimerStop=false;

	private Map<JButton, Timer> btnTimerMap = new HashMap<JButton, Timer>();
	private int delay;
	private int offset;
	
	
	/************************************System Parameters **************************************************/
	private String controlType;
	private String dwellTime;
	private String blockNo;
	
	private boolean isActiveSetting = false;
	
	
	/************************************Device Parameters **************************************************/
	private boolean isLogstared = false;
	private boolean isDeviceStarted = false;

	RetrieveDataThread retriThread;
	boolean isThreadStarted = false;
	
	/************************************Result Dialog Parameters********************************************/
	JDialog resultsDialog;
	JOptionPane resultsPane;
	JTextArea resultsArea;
	
	private List<String> clickedKeys = new ArrayList<String>();
	
	public static SoftKeyBoardMain getInstance() {
		if(softKeyboard == null) {
			softKeyboard = new SoftKeyBoardMain();
		}
		return softKeyboard;
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
	    System.out.println(controlType);
	    
	    System.out.println("dwellTime is changed"+ dwellTime);
	    System.out.println("blockNo is changed"+blockNo);
	    
	    /*
	     * Add glass pane on keyboard panel to avoid mouse click events
	     */
	    if(controlType.equals("Gaze Control")) {
	    	JPanel glass = new JPanel(new GridLayout(0, 1));
            // trap both mouse and key events.  Could provide a smarter 
            // key handler if you wanted to allow things like a keystroke 
            // that would cancel the long-running operation.
            glass.addMouseListener(new MouseAdapter() {});
            glass.addMouseMotionListener(new MouseMotionAdapter() {});
            glass.addKeyListener(new KeyAdapter() {});
            
            // Make the keyboard visible
            glass.setOpaque(false);
            // make sure the focus won't leave the glass pane
            setGlassPane(glass);
        	glass.setVisible(true);
	    }else {
	    	//Mouse control will set settings active as default
	    	isActiveSetting = true;
	    }
	    
	    p1.setOpaque(true); 
	    this.setContentPane(p1);
	    
	    WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(frame,
                        "Are You Sure to Close this Application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
                	
                	//Stop get data thread first and then shut down device
                	if(isThreadStarted) {
                		stopGazeControl();
                	}
                	try {
                		/**
                		 * Try to sleep for seconds and the shut down the device
                		 */
						Thread.sleep(300);
						if(isDeviceStarted) {
	                    	EyeDeviceControl.getInstance().stopLogging();
	                    	EyeDeviceControl.getInstance().shutdonwDevice();
	                	}
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	
                	//Start analysis fixation data when close the application window
                	if(controlType == "Mouse Control"){
                		MouseControlService service = new MouseControlService();
                		service.verifyFixtionData(AnalysisGazeLog.getInstance().getEyeGazeData());
                	}else if(controlType.equals("Gaze Control")) {
                		//Write fixation data into log file
                		EyeGazeRetrieveData.writeFixationDataLog();
                	}
                	System.exit(0);
                }else{
                	System.out.println("DO NOT close the window");
                }
            }
        };
        
        this.addWindowListener(exitListener);
	    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setVisible(true);
	    /*
	     * Add this line to avoid closing when press no button
	     */
	    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	    if(controlType == "Gaze Control" && isDeviceStarted){
	    	initilizeTimerForEachButton();
		    startGazeControl();
	    }
    }    
    
    public void startGazeControl() {
    	System.out.println("JAVA log: enter gaze control...");
    	retriThread = new RetrieveDataThread();
    	retriThread.run();
    	isThreadStarted = true;
    }
    
    private void stopGazeControl() {
    	System.out.println("JAVA log: shutting down the fetch data thread..");
    	if(isThreadStarted) {
    		retriThread.destory();    		
    	}
		EyeDeviceControl.getInstance().stopDataCollection();
		isThreadStarted = false;
    }
    
    /*
     * Build the text area
     */
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
		JPanel upper = new JPanel(new BorderLayout());
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.add(Box.createRigidArea(new Dimension(100, 30)));
		p1.add(text1Panel);
		p1.add(Box.createRigidArea(new Dimension(100, 30)));
		p1.add(text2Panel);
		p1.add(Box.createRigidArea(new Dimension(100, 30)));
		
		upper.add(p1,BorderLayout.LINE_START);
		upper.add(new Panel(), BorderLayout.LINE_END);
		
		JPanel keyboardPanel = new JPanel();
		keyboardPanel.add(createKeyboard());
		
		JPanel p = new JPanel(new BorderLayout());
		p.add(upper, "North");
		p.add(keyboardPanel, "Center");
		
		return p;
    }
    
    /*
     * Build the keyboard pane
     */
    public JLayeredPane createKeyboard() {
    	
    	//Adjust the key size with different screen size
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	double width = screenSize.getWidth();
    	double height = screenSize.getHeight();
    	
    	int w = (int) (width/12);
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
    	
    	keyboardLayerPanel = new Keyboard(qwertyKeyboard, this, controlType);
    	keyboardSet = qwertyKeyboard;
    	jbtnList = keyboardLayerPanel.getJButtonList();
    	progressBarList = keyboardLayerPanel.getProgressBarList();
		
		return keyboardLayerPanel;
    }
	
    /*
     * Load phrases from the files
     */
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
    	//presentedPhrase = phrases[r.nextInt(phrases.length)];
    	presentedPhrase = phrases[Integer.valueOf(blockNo) * sentenceSize];
    }
    
    public void showResultDialog() {
    	// prepare results dialog
    	resultsArea = new JTextArea(13, 40);
		resultsArea.setFont(new Font("sansserif", Font.PLAIN, 18));
		resultsArea.setBackground((new JButton()).getBackground());
		resultsPane = new JOptionPane(resultsArea, JOptionPane.INFORMATION_MESSAGE);
		resultsPane.setFont(new Font("sansserif", Font.PLAIN, 18));
		resultsDialog = resultsPane.createDialog(this, "Information");
		
		// build formated results to output to GUI
		StringBuilder resultsString = new StringBuilder();
		resultsString.append(" DATA COLLECTED:\n");
		resultsString.append(String.format("   Presented:\t\t%s\n", presentedPhrase));
		resultsString.append(String.format("   Transcribed:\t%s\n", clickedKeys.toString()));
		resultsString.append(String.format("   Keystrokes:\t\t%d\n", count));
		resultsString.append(String.format("   Characters:\t\t%d (%.1f words)\n", targetPhrase.length(), targetPhrase.length() / 5.0));
		resultsString.append(String.format("   Time:\t\t%.2f s (%.2f minutes)\n", t2 / 1000.0, t2 / 1000.0 / 60.0));
		//resultsString.append(String.format("   MSD:\t\t%d\n\n", msd));
		resultsString.append(" PARTICIPANT PERFORMANCE:\n");
		resultsString.append(String.format("   Entry Speed:\t%.2f wpm\n", wpm(targetPhrase, t2)));
		//resultsString.append(String.format("   Error rate:\t\t%.2f%%\n", s1s2.getErrorRateNew()));
		resultsString.append(String.format("   KSPC:\t\t%.4f\n", (double)count / targetPhrase.length()));
		resultsArea.setText(resultsString.toString());
		//resultsDialog.setVisible(true);
    }
    
    //Based on the x,y axis to analyze current button
    public String getKeyByPosition(int x, int y) {
    	//If current timer is not null
    	//Stop the timer and get a new timer from timer Map
    	if(timer != null) {
        	timer.stop();
        	System.out.println("current time is stopped");
    	}
    	hasPerformClick = false;
    	int realY = y- p1.getComponent(1).getY();
    	for(int i=0;i<keyboardSet.length;i++) {
    		KeyBt key = keyboardSet[i];
    		if(key.getX() < x && x < key.getX() + key.getW()) {
    			if(key.getY() < realY && realY < key.getY() + key.getH()) {
    				System.out.println("jbtn: "+jbtnList[i].getText());
    				
    				JButton jt = jbtnList[i];
    				System.out.println("Button from list:" + jt.getText());
					JProgressBar proBar = progressBarList[i];
					if(key.getLabel().equals(jt.getText())) {
						if(currentIndex != -1) {
							proBar.setValue(0);//Restart the circle count value from 0
							keyboardLayerPanel.setLayer(jbtnList[currentIndex], currentLayer++);
						}
						currentIndex = i;
						keyboardLayerPanel.setLayer(progressBarList[i], currentLayer++);
						
						timer = btnTimerMap.get(jt);
						timer.restart();
						System.out.println("timer is started!"+ key.getLabel() + " :" + System.currentTimeMillis());
					}
    				return key.getLabel();
    			}
    		}
    	}
    	return "";
    }
    
    /*
     * If lost gaze fixation then stop the process bar running on the current button
     */
    public void stopProgressBarTimer() {
    	timer.stop();
    }
    
    /**
     * Initialize a timer for each progress bar in order to fix the issue that
     * the first button always be clicked
     */
    private void initilizeTimerForEachButton() {
    	for(int i=0;i < jbtnList.length; i++) {
    		JProgressBar proBar = progressBarList[i];
    		JButton jbtn = jbtnList[i];
    		if(Integer.valueOf(dwellTime) <= 100) {
    			delay = Integer.valueOf(dwellTime)/25;
    			offset = 5;
    		}else {
    			delay = Integer.valueOf(dwellTime)/50;
    			offset = 2;
    		}
    		Timer timer = new Timer(delay, e -> {
    			if(!isTimerStop) {
    				int iv = Math.min(100, proBar.getValue() + offset);
    				proBar.setValue(iv);
  			      	if(proBar.getValue() == 100) {
  			      		System.out.println("timer is stoped!"+ jbtnList[currentIndex].getText() + " :" + System.currentTimeMillis());
  			      		if(!hasPerformClick) {
  			      			jbtn.doClick();
  			      			clickedKeys.add(jbtn.getText()+" ");
  			      			System.out.println("Calling doclick function by" + jbtnList[currentIndex].getText());
  			      			hasPerformClick = true;
  			      			//The button layer back to top again
  			      			keyboardLayerPanel.setLayer(jbtn, currentLayer++);
  			      		}
  			      	}
    			}
			});
    		btnTimerMap.put(jbtn, timer);
    	}
    }

	@Override
	public void actionPerformed(ActionEvent action) {
		// TODO Auto-generated method stub
		if(!isLogstared && isDeviceStarted) {
			EyeDeviceControl.getInstance().startLogging();
			EyeDeviceControl.getInstance().displayEyeImages();
			isLogstared = true;
		}
		
		JButton jb = (JButton)action.getSource();
		String s = jb.getText();
		char c = s.charAt(0);
		if(isShiftPress) {
			s = s.toUpperCase();
			isShiftPress = false;
		}else {
			c = s.toLowerCase().charAt(0);
		}
		//char c = (s.toLowerCase()).charAt(0);
		System.out.println(c);
		
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

		if (s.equals("Enter")) {
			enterPressed();
		}else if (s.equals("Bksp")) {
			if (targetPhrase.length() >= 1)
			{
				targetPhrase = targetPhrase.substring(0, targetPhrase.length() - 1);
				text2.setText(targetPhrase);
				text2.requestFocus(); // so I-beam (caret) does not disappear
			}
		}else if(s.equals("Setting")) {
			//TODO
			if(isActiveSetting) {
				activeSettingDialog();
			}
		}else if(s.equals("Shift")) {
			//TODO
			isShiftPress = true;
		}
		else
		// just a keystroke; add to transcribed text
		{
			targetPhrase += c;
			text2.setText(targetPhrase);
			text2.requestFocus(); // so I-beam (caret) does not disappear
		}
		
	}
	
	//Active the setting dialog and stop all the gaze operation
	private void activeSettingDialog() {
		System.out.println("settings button is pressed");
		int confirm = JOptionPane.showOptionDialog(this,
                "Please note that if the settings changed then the device would be recalibrate",
                "Information", 
                JOptionPane.OK_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, null, null);
		//Stop current data collection
		stopGazeControl();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	SettingDialog soft = new SettingDialog();
            	soft.createDialog();
            }
       });
	}
	
	/*
	 * When press enter button
	 * 1. Write click log
	 * 2. if finish current block then pop up notification else Load next phrase
	 */
	private void enterPressed() {
		finishCount++;
		
		//Initialize the output data file
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss-ddMM-yyyy");
        System.out.println( sdf.format(cal.getTime()) );
		//s1 = sdf.format(cal.getTime())+"-"+controlType+".dat";
		String si = "ClickInfo"+"_"+finishCount+sdf.format(cal.getTime())+".dat";
		
		WriteClickLog.getInstance().CfgWriter(targetPhrase, presentedPhrase, samples, si);

		// prepare for next phrase
	    samples = new Vector<Sample>();
	    
	    if(finishCount == sentenceSize) {

			//if it is the end of the block , stop the data collection thread
	    	stopGazeControl();
	    	
			JLabel thankyou = new JLabel("End of This Session. Thank you.");
			thankyou.setFont(new Font("sansserif", Font.PLAIN, 16));
			JOptionPane.showMessageDialog(this, thankyou);
		}else {
		    //Increase the phrase every time click the enter
		    int cout = Integer.valueOf(blockNo);
			presentedPhrase = phrases[cout+finishCount];
			
			text1.setText(presentedPhrase);
			targetPhrase = "";
			text2.setText(targetPhrase);
			t1 = 0;
			count = 0;
		}
	    
	    showResultDialog();
	}
	
	
	// compute typing speed in wpm given text entered and time in ms
	public static double wpm(String text, long msTime)
	{
		double speed = text.length();
		speed = speed / (msTime / 1000.0) * (60 / 5);
		return speed;
	}
	
	public JPanel getMainPanel() {
		return p1;
	}
    
	/*
	 * Get selected control type
	 */
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	
	/*
	 * Get dwell time settings
	 */
	public void setDwellTime(String dwellTime) {
		this.dwellTime = dwellTime;
	}
	
	public void setBlockNo(String block) {
		this.blockNo = block;
	}
	
	public void setSentenceSize(int size) {
		this.sentenceSize = size;
	}
	
	public void isActiveSetting(boolean isactive) {
		this.isActiveSetting = isactive;
	}
}
