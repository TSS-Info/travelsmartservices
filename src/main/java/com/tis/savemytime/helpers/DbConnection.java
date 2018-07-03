package com.tis.savemytime.helpers;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;



public class DbConnection {

	private static final Logger logger = Logger.getLogger(DbConnection.class);
	
	
	public Connection getConnection() {
		Context ctx = null;
		Connection con = null;
		
		try {/*
			String connectionURL = PropertiesHelper.getPropertyValue("jdbc_url");
			System.out.println("jdbc url::"+connectionURL);
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String jdbcUsername = PropertiesHelper.getPropertyValue("jdbc_username");
			String jdbcPassword = PropertiesHelper.getPropertyValue("jdbc_password");
			connection = DriverManager.getConnection(connectionURL, jdbcUsername, jdbcPassword);*/
			ctx = new InitialContext();
			logger.debug("initial context has been set");
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/SMTDS");
			logger.debug("data source has been created");
			con = ds.getConnection();
			return con;
		} catch (SQLException e) {
			logger.error(e);
			return null;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

}