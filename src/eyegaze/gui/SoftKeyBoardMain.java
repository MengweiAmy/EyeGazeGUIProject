package eyegaze.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	JButton[] jbtnList;
	
	private JTextField text1;
	private JTextField text2;
	
	private BufferedWriter sd1File;
	
	private String[] phrases;
	private String presentedPhrase; // presented phrase
	private String targetPhrase; // presented phrase
	
	private Vector<Sample> samples= new Vector<Sample>();;
	
	private long t1, t2;
	private int count; // count keystrokes per phrase
	private Random r = new Random();//generate random index to display random sentence from phrase file
	
	//static EyeGazeJNI eyeGaze = new EyeGazeJNI();
	
	private String controlType;
	
	public SoftKeyBoardMain(String controlType) {
		this.controlType = controlType;
	}

    public void createAndShowGUI() {
	    JFrame frame = this;
	    frame.setTitle("Current Control Type:" + controlType);
	    JPanel p1 = createTextField();
	    this.setContentPane(p1);
	    WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(frame,
                        "Are You Sure to Close this Application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
//                    eyeGaze.EyeGazeLogStop();
//                    eyeGaze.EyeGazeShutDown();
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
		
		//Initialize the output files
		String s1 = "";
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-ddMM-HHmmss");
        System.out.println( sdf.format(cal.getTime()) );
		s1 = sdf.format(cal.getTime())+".sd";
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
    	for(int i=0;i<keyboardSet.length;i++) {
    		KeyBt key = keyboardSet[i];
    		if(key.getX() < x && x < key.getX() + key.getW()) {
    			if(key.getY() < y && y < key.getY() + key.getH()) {
    				System.out.println("jbtn"+jbtnList[i].getText());
    				jbtnList[i].doClick();
    				return key.getLabel();
    			}
    		}
    	}
    	return "";
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
//		int result = eyeGaze.EyeGazeInit(0);
//		System.out.print("start device....." + result +"\n'");
//		  
//		System.out.print("start calibrating device.....\n");
//		eyeGaze.Calibrate();
//		
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//            	SoftKeyBoardMain soft = new SoftKeyBoardMain();
//            	soft.createAndShowGUI();
//            	
//            	//String value = soft.getKeyByPosition(360,200);
//            	//System.out.println(value);
//            }
//        });
//        
//		int startSta = eyeGaze.EyeGazeLogStart();
//		if(startSta == 0) {
//			System.out.print("start logging data in trace.dat file.....\n");
//		}else {
//			System.out.print("start logging failed.....\n");
//		}
		
//        new Thread() {
//        	public void run() {
//        		Runnable helloRunnable = new Runnable() {
//        		    public void run() {
//        		        System.out.println("Hello world");
//        		        EyeGazeData[] data = eyeGaze.getEyeGazeData();
//                		if(data != null) {
//                			for(int i=0;i<data.length;i++) {
//                    			int count = data[i].getAppMarkCount();
//                    			System.out.println("data collected....." + count);
//                    			System.out.println("data getiIGaze....." + data[i].getiIGaze());
//                    			System.out.println("data getfXEyeballOffsetMm....." + data[i].getfXEyeballOffsetMm());
//                    			System.out.println("data getiJGaze....." + data[i].getiJGaze());
//                    			System.out.println("data getCameraFieldCount....." + data[i].getCameraFieldCount());
//                			}
//                		}else {
//                			System.out.println("no data collected.....");
//                		}
//        		    }
//        		};
//
//        		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//        		executor.scheduleAtFixedRate(helloRunnable, 0, 300, TimeUnit.MILLISECONDS);
//        	}
//        }.start();
		
//	    EyeGazeData[] data = eyeGaze.getEyeGazeData();
// 		if(data != null) {
// 			System.out.println("data is not null.....");
// 			for(int i=0;i<data.length;i++) {
//     			int count = data[i].getAppMarkCount();
//     			System.out.println("data collected....." + count);
//     			System.out.println("data getiIGaze....." + data[i].getiIGaze());
//     			System.out.println("data getfXEyeballOffsetMm....." + data[i].getfXEyeballOffsetMm());
//     			System.out.println("data getiJGaze....." + data[i].getiJGaze());
//     			System.out.println("data getCameraFieldCount....." + data[i].getCameraFieldCount());
// 			}
// 		}else {
// 			System.out.println("no data collected.....");
// 		}
//		  
//		Timer timer = new Timer();
//		  timer.schedule(new TimerTask() {
//			  @Override
//			  public void run() {
//			    // Your database code here
//				  eyeGaze.EyeGazeLogStop();
//				  eyeGaze.EyeGazeShutDown();
//			  }
//			}, 10*1000);
    }

	@Override
	public void actionPerformed(ActionEvent action) {
		// TODO Auto-generated method stub
		JButton jb = (JButton)action.getSource();
		String s = jb.getText();
		char c = (s.toLowerCase()).charAt(0);
		
		++count;
		if (t1 == 0)
			t1 = System.currentTimeMillis();
		t2 = System.currentTimeMillis() - t1;
		samples.addElement(new Sample(t2, s.toLowerCase()));
		
		
		if (s.equals("Enter")){
			//TODO Finish current input and write the log
			//Stop logging the eyegaze data if the eye tracker is started
			String s2 = text2.getText().toLowerCase();
			
			// build output data for sd1 file
			StringBuilder sd1Stuff = new StringBuilder();
			sd1Stuff.append(presentedPhrase).append('\n');
			sd1Stuff.append(s2).append('\n');
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
			
		}
		else
		// just a keystroke; add to transcribed text
		{
			targetPhrase += c;
			text2.setText(targetPhrase);
			text2.requestFocus(); // so I-beam (caret) does not disappear
		}
		
	}
    
}
