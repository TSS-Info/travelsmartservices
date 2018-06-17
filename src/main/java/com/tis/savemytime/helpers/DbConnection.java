package com.tis.savemytime.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DbConnection {

	public Connection getConnection() {
		try {
			String connectionURL = PropertiesHelper.getPropertyValue("jdbc_url");
			System.out.println("jdbc url::"+connectionURL);
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String jdbcUsername = PropertiesHelper.getPropertyValue("jdbc_username");
			String jdbcPassword = PropertiesHelper.getPropertyValue("jdbc_password");
			connection = DriverManager.getConnection(connectionURL, jdbcUsername, jdbcPassword);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}