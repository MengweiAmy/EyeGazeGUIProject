package eyegaze.gui.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import eyegaze.gui.model.Configuration;

public class ConfigurationService {
	
	private static ConfigurationService config;
	
	public static ConfigurationService getInstance() {
		if(config == null) {
			config = new ConfigurationService();
		}
		return config;
	}
	
	public void writeConfig(Configuration config) {
		File configFile = new File("config.properties");
		 
		try {
		    Properties props = new Properties();
		    props.setProperty("controlType", config.getControlType()+"");
		    props.setProperty("dwellTime", config.getDwellTime()+"");
		    props.setProperty("FixationSamples", config.getFixationSamples()+"");
		    props.setProperty("FixationOffset", config.getFixationOffset()+"");
		    FileWriter writer = new FileWriter(configFile);
		    props.store(writer, "Parameter Settings");
		    writer.close();
		} catch (FileNotFoundException ex) {
		    // file does not exist
		} catch (IOException ex) {
		    // I/O error
		}
	}
	
	public Configuration loadConfig() {
		File configFile = new File("config.properties");
		Configuration config = new Configuration();
		try {
		    FileReader reader = new FileReader(configFile);
		    Properties props = new Properties();
		    props.load(reader);
		    
		    String type = props.getProperty("controlType");
		    config.setControlType(Integer.valueOf(type));
		 
		    String dwellTime = props.getProperty("dwellTime");
		    config.setDwellTime(Integer.valueOf(dwellTime));
		    
		    String fixaSam = props.getProperty("FixationSamples");
		    config.setFixationSamples(Integer.valueOf(fixaSam));
		    
		    String fixaOff = props.getProperty("FixationOffset");
		    config.setFixationOffset(Integer.valueOf(fixaOff));
		    reader.close();
		} catch (FileNotFoundException ex) {
		    // file does not exist
		} catch (IOException ex) {
		    // I/O error
		}
		return config;
	}

}
