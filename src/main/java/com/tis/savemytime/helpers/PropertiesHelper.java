package com.tis.savemytime.helpers;

import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesHelper {
		public static Properties prop = new Properties();
		public static ResourceBundle input = null;
		
		public static String getPropertyValue(String key) {
			
		input = ResourceBundle.getBundle("config");
	
		// load a properties file
		//prop.load(input);
		
		return input.getString(key);
	}
}
