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
	
	private JComboBox dwellCombo;
	
	private String[] sampleList = {"20","50","75","100","150","200"};
	
	private String[] offsetList = {"30","50","65","75","100"};
	
	private String[] dwellList = {"50ms", "100ms","200ms","250ms","300ms","500ms"};

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
	     String[] currTypes = null;
	     for (int i = 0; i < numPairs; i++) {
	         JLabel l = new JLabel(labels[i], JLabel.TRAILING);
	         p.add(l);
	         if(i ==0) {
	        	 currTypes = types;
	        	 controlTypeCombo = new JComboBox(currTypes);
	        	 l.setLabelFor(controlTypeCombo);
		         p.add(controlTypeCombo);
	         }else if(i==1) {
	        	 currTypes = sampleList;
	        	 fixationSampleCombo = new JComboBox(currTypes);
	        	 l.setLabelFor(fixationSampleCombo);
		         p.add(fixationSampleCombo);
	         }else if(i==2){
	        	 currTypes = offsetList;
	        	 fixationOffsetCombo = new JComboBox(currTypes);
	        	 l.setLabelFor(fixationOffsetCombo);
		         p.add(fixationOffsetCombo);
	         }else if(i==3) {
	        	 currTypes = dwellList;
	        	 dwellCombo = new JComboBox(currTypes);
	        	 l.setLabelFor(dwellCombo);
		         p.add(dwellCombo);
	         }
	     }

	     //Lay out the panel.
	     SpringUtilities.makeCompactGrid(p,
	                                     numPairs, 2, //rows, cols
	                                     2, 3,        //initX, initY
	                                     2, 3);       //xPad, yPad

	     JPanel text1Panel = new JPanel();
		 text1Panel.setLayout(new BoxLayout(text1Panel, BoxLayout.PAGE_AXIS));
		 text1Panel.setBorder(new TitledBorder(new EtchedBorder(), "Configuration"));

		 JPanel text2Panel = new JPanel();
		 text2Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		 
		 JButton b=new JButton("OK");  
		 b.setBounds(50,100,95,30);  
		 
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
			        
			        String dwellTime = (String) dwellCombo.getSelectedItem();
			        String time = dwellTime.substring(0, dwellTime.length()-2);
			        
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
			            	SoftKeyBoardMain.getInstance().setControlType(controlTye);
			            	SoftKeyBoardMain.getInstance().setDwellTime(time);
			            	SoftKeyBoardMain.getInstance().createAndShowGUI(); 
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
	                    System.exit(0);
	                }
	            }
	        };
	      this.addWindowListener(exitListener);
		  this.setExtendedState(JFrame.NORMAL);
		  this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
