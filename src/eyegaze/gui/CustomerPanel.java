package eyegaze.gui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class CustomerPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<Circle> circles = new ArrayList<>();
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Circle circle : circles) {
            circle.draw(g);
        }
    }

    public void addCircle(Circle circle) {
    	circles = new ArrayList<>();
        circles.add(circle);
        repaint();
    }

}
