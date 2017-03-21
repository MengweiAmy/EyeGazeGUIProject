package eyegaze.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import eyegaze.gui.tools.SpringUtilities;

/**
 * Application entry to choose the control type : 
 * 		Mouse control
 * 		Gaze control
 * @author EYEGAZE 2.3 i7
 *
 */
public class SettingDialog extends JFrame implements ActionListener{
	
	private JFrame frame;
	
	int controlType=0;
	
	private JComboBox controlTypeCombo;
	
	private JComboBox fixationSampleCombo;
	
	private JComboBox fixationOffsetCombo;
	
	String[] sampleList = {"20","50","75","100","150","200"};
	
	String[] offsetList = {"30","50","65","75","100"};
	
	String[] dwellList = {"50", "100","200","300","500"};

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void createDialog() {
		 JFrame frame = this;
		 int w = 600;
		 int h = 300;
		 setSize(new Dimension(w, h));
	     frame.setLocationRelativeTo(null);
	     frame.setTitle("Settings");
	     
	     String[] labels = {"Select Control Type:", "Select Minimum fixation samples:", "Select Minimum fixation offset:", "Select dwell time(millSec):"};
	     
	     String[] types = { "Mouse Control", "Gaze Control"};

	     int numPairs = labels.length;

	     //Create and populate the panel.
	     JPanel p = new JPanel(new SpringLayout());
	     for (int i = 0; i < numPairs; i++) {
	         JLabel l = new JLabel(labels[i], JLabel.TRAILING);
	         p.add(l);
	         if(i==1) {
	        	 types = sampleList;
	         }else if(i==2){
	        	 types = offsetList;
	         }else if(i==3) {
	        	 types = dwellList;
	         }
	         JComboBox textField = new JComboBox(types);
	         l.setLabelFor(textField);
	         p.add(textField);
	     }

	     //Lay out the panel.
	     SpringUtilities.makeCompactGrid(p,
	                                     numPairs, 2, //rows, cols
	                                     2, 3,        //initX, initY
	                                     2, 3);       //xPad, yPad

	     JPanel text1Panel = new JPanel();
		 text1Panel.setLayout(new BoxLayout(text1Panel, BoxLayout.PAGE_AXIS));
		 text1Panel.setBorder(new TitledBorder(new EtchedBorder(), "Configuration"));
		 
		 JLabel label1 = new JLabel("Select Control Type:");
		 
		 controlTypeCombo = new JComboBox(types);
		 controlTypeCombo.setSelectedIndex(0);
		 controlTypeCombo.addActionListener(this);
		 controlTypeCombo.setPreferredSize(new Dimension(200,30));
		 
		 JLabel label2 = new JLabel("Select Minimum fixation samples:");
		 fixationSampleCombo = new JComboBox(sampleList);
		 fixationSampleCombo.setSelectedIndex(0);
		 fixationSampleCombo.addActionListener(this);
		 fixationSampleCombo.setPreferredSize(new Dimension(200,30));
		 
		 JLabel label3 = new JLabel("Select Minimum fixation offset:");
		 fixationOffsetCombo = new JComboBox(sampleList);
		 fixationOffsetCombo.setSelectedIndex(0);
		 fixationOffsetCombo.addActionListener(this);
		 fixationOffsetCombo.setPreferredSize(new Dimension(200,30));
		 
		 
		 JPanel text2Panel = new JPanel();
		 text2Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		 
		 JButton b=new JButton("OK");  
		 b.setBounds(50,100,95,30);  
			
		 text1Panel.add(label1,BorderLayout.CENTER);
		 text1Panel.add(controlTypeCombo,BorderLayout.CENTER);
		 
		 text1Panel.add(label2,BorderLayout.CENTER);
		 text1Panel.add(fixationSampleCombo,BorderLayout.CENTER);
		 
		 text1Panel.add(label3,BorderLayout.CENTER);
		 text1Panel.add(fixationOffsetCombo,BorderLayout.CENTER);
		 
		 text2Panel.add(b,BorderLayout.CENTER);
		 
		 JPanel p1 = new JPanel();
		 p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
			
		 p1.add(p);
		 p1.add(text2Panel);
		 this.setContentPane(p1);
		 
		 b.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent event) {
			        String controlTye = (String) controlTypeCombo.getSelectedItem();
			        javax.swing.SwingUtilities.invokeLater(new Runnable() {
			            public void run() {
			            	if(controlTye.equals("Gaze Control")) {
			            		int confirm = JOptionPane.showOptionDialog(frame,
			                            "Please note that the mouse click event will be disabled when you choose gaze control",
			                            "Information", 
			                            JOptionPane.OK_OPTION,
			                            JOptionPane.PLAIN_MESSAGE, null, null, null);
			            	}
			            	dispose();//close current setting window and open a new keyboard one
			            	
			            	/*
			            	 * Set controlType before start the application
			            	 */
			            	//SoftKeyBoardMain.getInstance().setControlType(controlTye);
			            	//SoftKeyBoardMain.getInstance().createAndShowGUI(); 
			            }
			        });
			    }
			});
		 
	     WindowListener exitListener = new WindowAdapter() {

	            @Override
	            public void windowClosing(WindowEvent e) {
	                int confirm = JOptionPane.showOptionDialog(frame,
	                        "Are You Sure to Close this Application?",
	                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
	                        JOptionPane.QUESTION_MESSAGE, null, null, null);
	                if (confirm == JOptionPane.YES_OPTION) {
//	                    eyeGaze.EyeGazeLogStop();
//	                    eyeGaze.EyeGazeShutDown();
	                    System.exit(0);
	                }
	            }
	        };
	        this.addWindowListener(exitListener);
		    this.setExtendedState(JFrame.NORMAL);
		    this.setVisible(true);
	}
	
	public static void main(String[] arg0) {
		
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	SettingDialog soft = new SettingDialog();
            	soft.createDialog();
            }
        });

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
