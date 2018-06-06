package com.tis.savemytime.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DbConnection {

	public Connection getConnection() throws Exception {
		try {
			String connectionURL = PropertiesHelper.getPropertyValue("jdbc_url");
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, PropertiesHelper.getPropertyValue("jdbc_username"), PropertiesHelper.getPropertyValue("jdbc_password"));
			return connection;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

}