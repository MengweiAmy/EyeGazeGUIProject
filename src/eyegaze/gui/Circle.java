package eyegaze.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Circle {
	
	 int x, y, width, height;

     public Circle(int xpos, int ypos) {
    	 this.x = xpos;
    	 this.y = ypos;
     }

     public void draw(Graphics g) {
         Graphics2D gr = (Graphics2D)g.create();

         gr.setRenderingHint(
             RenderingHints.KEY_ANTIALIASING,
             RenderingHints.VALUE_ANTIALIAS_ON);

         Shape ring = createRingShape(x, y, 10, 2); 
         gr.setColor(Color.RED);//(new Color(135, 176, 242));
         gr.fill(ring);
         gr.setColor(Color.BLACK);
         gr.draw(ring);
     }
     
     private Shape createRingShape(
    	        double centerX, double centerY, double outerRadius, double thickness)
    	    {
    	        Ellipse2D outer = new Ellipse2D.Double(
    	            centerX - outerRadius, 
    	            centerY - outerRadius,
    	            outerRadius + outerRadius, 
    	            outerRadius + outerRadius);
    	        Ellipse2D inner = new Ellipse2D.Double(
    	            centerX - outerRadius + thickness, 
    	            centerY - outerRadius + thickness,
    	            outerRadius + outerRadius - thickness - thickness, 
    	            outerRadius + outerRadius - thickness - thickness);
    	        Area area = new Area(outer);
    	        area.subtract(new Area(inner));
    	        return area;
    	    }

}
