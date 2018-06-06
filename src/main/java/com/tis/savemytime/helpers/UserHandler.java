package com.tis.savemytime.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.tis.savemytime.models.User;


public class UserHandler {

	public boolean saveUser(Connection connection, User user) throws Exception {
		try {
			String query = "insert into user(FIRST_NAME,LAST_NAME,USER_NAME,PASSWORD, PASSWORD_CREATION_DATE,DISTRICT_VILLAGE_NAME,UPDATE_STAMP,ADDRESS_ID,IDIDENTITY,card_id) values(?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = null;
			
			ps = connection.prepareStatement(query);
			
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getUserName());
			ps.setString(4, user.getPassword());
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			ps.setString(5,date.toString());
			ps.setString(6, user.getDistrictVillageName());
			ps.setString(7, user.getTimeStamp());
			ps.setInt(8, user.getAddressID());
			ps.setInt(9, user.getIdentityID());
			ps.setInt(10, user.getCardID());
			
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			//throw e;
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<User> getUsers(Connection connection) throws Exception {
		ArrayList<User> userTransList = new ArrayList<User>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM USER");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserID(rs.getInt("USER_ID"));
				user.setFirstName(rs.getString("FIRST_NAME"));
				user.setLastName(rs.getString("LAST_NAME"));
				user.setUserName(rs.getString("USER_NAME"));
				userTransList.add(user);
			}
			return userTransList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public User getUserByID(Connection connection, int id) throws Exception {
		User user = new User();
		try {
			System.out.println("user id::"+id);
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM USER where USER_ID="+id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user.setUserID(rs.getInt("USER_ID"));
				user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("FIRST_NAME"));
				user.setLastName(rs.getString("LAST_NAME"));
				user.setUserName(rs.getString("USER_NAME"));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public User authenticateUser(Connection connection, User user) {
		// TODO Auto-generated method stub
		try {
			System.out.println("user id::"+user.getUserName());
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM USER where USER_NAME ='"+user.getUserName()+"' and PASSWORD='"+user.getPassword()+"'");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user.setUserID(rs.getInt("USER_ID"));
				//user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("FIRST_NAME"));
				user.setLastName(rs.getString("LAST_NAME"));
				//user.setUserName(rs.getString("USER_NAME"));
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}