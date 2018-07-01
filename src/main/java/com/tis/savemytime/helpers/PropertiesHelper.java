package com.tis.savemytime.helpers;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class PropertiesHelper {
	private static final Logger logger = Logger.getLogger(PropertiesHelper.class);
	
	//	public static Properties prop = new Properties();
		public static ResourceBundle input = null;
		
		public static String getPropertyValue(String key) {
			
		input = ResourceBundle.getBundle("config");
		logger.debug("resource bundle has been identified.."+input);
		// load a properties file
		//prop.load(input);
		
		return input.getString(key);
	}
}
