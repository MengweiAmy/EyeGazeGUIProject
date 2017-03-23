package eyegaze.gui.tools;

/**
 * Save and load config file
 * @author EYEGAZE 2.3 i7
 *
 */
public class ConfigUtilities {
	
	
	private ConfigUtilities config;
	
	public ConfigUtilities getInstance() {
		if(config == null) {
			config = new ConfigUtilities();
		}
		return config;
	}
	
	public void saveConfiguration() {
		
	}

}
