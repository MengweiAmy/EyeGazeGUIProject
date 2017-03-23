package eyegaze.gui;

/**
 * Main entrance of the application
 * @author EYEGAZE 2.3 i7
 *
 */
public class Application {
	
	public static void main(String[] arg0s) {
		  javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	SettingDialog soft = new SettingDialog();
	            	soft.createDialog();
	            }
	       });
	}

}
