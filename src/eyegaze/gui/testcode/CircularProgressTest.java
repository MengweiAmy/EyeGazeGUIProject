package eyegaze.gui.testcode;



import java.awt.*;
import javax.swing.*;

import eyegaze.gui.ProgressCircleUI;

public class CircularProgressTest {
	
	private int delay;
	
	private int offset;
	
	public JComponent makeUI(String dwellTime) {
	    JProgressBar progress = new JProgressBar();
	    // use JProgressBar#setUI(...) method
	    progress.setUI(new ProgressCircleUI());
	    progress.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
	    progress.setStringPainted(true);
	    progress.setFont(progress.getFont().deriveFont(24f));
	    progress.setForeground(Color.ORANGE);

	    if(Integer.valueOf(dwellTime) <= 100) {
			delay = Integer.valueOf(dwellTime)/10;
			offset = 10;
		}else {
			delay = Integer.valueOf(dwellTime)/100;
			offset = 1;
		}
	    
	    (new Timer(delay, e -> {
	      int iv = Math.min(100, progress.getValue() + offset);
	      progress.setValue(iv);
	    })).start();

	    JPanel p = new JPanel();
	    p.add(progress);
	    return p;
	  }
	  public static void main(String... args) {
	    EventQueue.invokeLater(() -> {
	      JFrame f = new JFrame();
	      f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	      JPanel newPanel = new JPanel(new CardLayout());
	      newPanel.add(new CircularProgressTest().makeUI("500"));
	      
	      //f.getContentPane().add(new CircularProgressTest().makeUI());
	      f.setSize(320, 240);
	      f.setLocationRelativeTo(null);
	      f.setVisible(true);
	      
	      f.setContentPane(newPanel);
	    });
	  }

}
