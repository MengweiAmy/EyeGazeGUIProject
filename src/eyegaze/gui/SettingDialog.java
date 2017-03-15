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
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SettingDialog extends JFrame implements ActionListener{
	
	private JFrame frame;
	
	int controlType=0;
	
	private JComboBox controlTypeCombo;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void createDialog() {
		 JFrame frame = this;
		 int w = 600;
		 int h = 400;
		 setSize(new Dimension(w, h));
	     frame.setLocationRelativeTo(null);
	     frame.setTitle("Settings");

	     JPanel text1Panel = new JPanel();
		 text1Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		 text1Panel.setBorder(new TitledBorder(new EtchedBorder(), "Configuration"));
		 
		 String[] types = { "Mouse Control", "Gaze Control"};

			//Create the combo box, select item at index 4.
			//Indices start at 0, so 4 specifies the pig.
		 
		 JLabel label1 = new JLabel("Select Control Type:");
		 
		 controlTypeCombo = new JComboBox(types);
		 controlTypeCombo.setSelectedIndex(0);
		 controlTypeCombo.addActionListener(this);
		 controlTypeCombo.setPreferredSize(new Dimension(200,30));
		 
		 
		 JPanel text2Panel = new JPanel();
		 text2Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		 
		 JButton b=new JButton("OK");  
		 b.setBounds(50,100,95,30);  
		    
			
		 text1Panel.add(label1,BorderLayout.CENTER);
		 text1Panel.add(controlTypeCombo,BorderLayout.CENTER);
		 
		 text2Panel.add(b,BorderLayout.CENTER);
		 
		 JPanel p1 = new JPanel();
		 p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
			
		 p1.add(text1Panel);
		 p1.add(text2Panel);
		 this.setContentPane(p1);
		 
		 b.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent event) {
			        String selectedBook = (String) controlTypeCombo.getSelectedItem();
			        if (selectedBook.equals("Mouse Controll")) {
			        	controlType = 0;
			        } else if (selectedBook.equals("Gaze Control")) {
			            System.out.println("Nice pick, too!");
			            controlType = 1;
			        }
			        javax.swing.SwingUtilities.invokeLater(new Runnable() {
			            public void run() {
			            	dispose();//clost current setting window and open a new keyboard one
			            	SoftKeyBoardMain soft = new SoftKeyBoardMain(selectedBook);
			            	soft.createAndShowGUI();
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
            	
            	//String value = soft.getKeyByPosition(360,200);
            	//System.out.println(value);
            }
        });

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
