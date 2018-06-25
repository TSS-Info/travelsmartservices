package com.tis.savemytime.helpers;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.tis.savemytime.models.Status;
import com.tis.savemytime.models.User;


public class UserHandler {

	public boolean saveUser(Connection connection, User user) throws Exception {
		try {
			String query = "insert into user(FIRST_NAME,LAST_NAME,USER_NAME,PASSWORD, PASSWORD_CREATION_DATE,DISTRICT_VILLAGE_NAME,UPDATE_STAMP,ADDRESS_ID,IDIDENTITY,card_id,mobileNo, dob) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			
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
			ps.setString(11, user.getMobileNo());
			ps.setString(12, user.getDob());
			
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
		finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	public Status sendVerificationEmail(Connection connection, User user, String emailLink) {
		// TODO Auto-generated method stub
		String tokenLength = PropertiesHelper.getPropertyValue("activation_code_length");
		String emailDomain = PropertiesHelper.getPropertyValue("email_link_domain");
		String emailSignature = PropertiesHelper.getPropertyValue("email_signature");
		String emailSubject = PropertiesHelper.getPropertyValue("email_subject");
		
		String hostname = PropertiesHelper.getPropertyValue("email_smtp_host");
		String fromEmail = PropertiesHelper.getPropertyValue("email_from");
		String password = PropertiesHelper.getPropertyValue("email_from_password");
		String noReplyID = PropertiesHelper.getPropertyValue("email_no_reply_id");
		String noReplyIDMSG = PropertiesHelper.getPropertyValue("email_no_reply_id_msg");
		String sslEnable = PropertiesHelper.getPropertyValue("email_ssl_enable");
		
		int hostPort = Integer.parseInt(PropertiesHelper.getPropertyValue("email_smtp_port"));
		
		String toEmail = user.getUserName();
		
		Date today = new Date();
		long expiryTimeInMills = UserHandler.add(today, Calendar.MILLISECOND, Integer.parseInt(PropertiesHelper.getPropertyValue("activation_code_expiry")));
		
		int tokenLen = 5;
		Status status = new Status();
		
		if(tokenLength!=null && !"".equalsIgnoreCase(tokenLength)) {
			tokenLen = Integer.parseInt(tokenLength);
			status.setMessage("token length has been taken from properties.."+tokenLen);
			status.setStatus("Success");
		}
		String token = UserHandler.getToken(tokenLen);
		status.setMessage("token has been created successfully"+token);
		status.setStatus("Success");
		status = this.saveAccessToken(connection,token, user.getUserID(), expiryTimeInMills);
		if(status!=null && status.getStatus().equalsIgnoreCase("Success"))
		{
			String emailFullLink = emailDomain + emailLink +"?userId="+user.getUserID()+"&accessToken="+token;
			String body = "Hi "+user.getFirstName()+"<br/><br/>Please click <a href='"+emailFullLink+"'>here</a> to verify your email address.<br/></br>"+emailSignature;
			
			try {
				try {
					status = EmailHandler.sendEmail(noReplyID,noReplyIDMSG,sslEnable, hostname, hostPort, fromEmail, toEmail, emailSubject, body, password);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					status.setMessage(" Unsupported encoding.."+e.getMessage());
					status.setStatus("Failure");
				}
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				status.setMessage("Address is not valid.."+e.getMessage());
				status.setStatus("Failure");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				status.setMessage("Message is not valid.."+e.getMessage());
				status.setStatus("Failure");
			}
		}
		return status;
	}
	
	
	private static long add(Date date, int calendarField, int amount) {
	      if (date == null) {
	          throw new IllegalArgumentException("The date must not be null");
	      }
	      Calendar c = Calendar.getInstance();
	      c.setTime(date);
	      c.add(calendarField, amount);
	      return c.getTime().getTime();
	  }
	
	private Status saveAccessToken(Connection connection, String token, int userId, double expiryTime)
	{
		Status status = new Status();
		try {
			String query = "insert into USER_VERIFICATION(USER_ID,ACCESS_TOKEN,EMAIL_LINK_CHK,ACCESS_TOKEN_EXP,SMS_CHK,CREATED_DATE) values(?,?,?,?,?,now())";
			
			PreparedStatement ps = null;
			
			ps = connection.prepareStatement(query);
			
			ps.setInt(1, userId);
			ps.setString(2, token);
			ps.setString(3, "N");
			ps.setDouble(4, expiryTime);
			ps.setString(5, "N");
			ps.executeUpdate();
			status.setMessage("Successfull saved the records");
			status.setStatus("Success");
			//return true;
		} catch (Exception e) {
			//throw e;
			e.printStackTrace();
			status.setMessage("Failed to saved the records.."+e.getMessage());
			status.setStatus("Failure");
		}
		return status;
	}
	
	private static final Random random = new Random();
	private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890!@#$";

	public static String getToken(int length) {
	    StringBuilder token = new StringBuilder(length);
	    for (int i = 0; i < length; i++) {
	        token.append(CHARS.charAt(random.nextInt(CHARS.length())));
	    }
	    return token.toString();
	}

	public Status verifyEmail(Connection connection, String userId, String accessToken) {
		// TODO Auto-generated method stub
		Status status = this.getTokenFromDB(connection, userId);
		
		if(status.getStatusCode().equalsIgnoreCase("200") ) {
			if(status.getStatus().equalsIgnoreCase(accessToken)) {
				status = this.updateRegistrationSuccess(connection, userId, status.getMessage());
			}
			else {
				status.setStatusCode("2101");
				status.setMessage("Invalid token!!");
				status.setStatus("Failure");
			}
		}
		
		
		return status;
	}

	private Status updateRegistrationSuccess(Connection connection, String userId, String createdDate) {
		// TODO Auto-generated method stub
		Status status = new Status();
		try {
			
			PreparedStatement ps = connection.prepareStatement("update USER_VERIFICATION set remarks = concat('success - ', now()), EMAIL_LINK_CHK = 'Y' where user_id=? and created_date = ?");
			ps.setString(1, userId);
			ps.setString(2, createdDate);
			
			int i = ps.executeUpdate();
			if(i>0)
			{
				status.setStatusCode("200");
				status.setMessage("Updated your registration verification status");
				status.setStatus("Success");
			}
			else {
				status.setStatusCode("200");
				status.setMessage("No records found or something went wrong..");
				status.setStatus("Failure");
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			status.setStatus("500");
			status.setMessage("SQL Exception 338:"+e.getMessage());
			status.setStatus("Failure");
			return status;
		}
	}
	
	private Status getTokenFromDB(Connection connection, String userId) {
		// TODO Auto-generated method stub
		Status status = new Status();
		try {
			
			PreparedStatement ps = connection.prepareStatement("SELECT access_token,created_date FROM USER_VERIFICATION where user_id="+userId+" and EMAIL_LINK_CHK='N' order by CREATED_DATE desc");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				 status.setStatus(rs.getString("access_token"));
				 status.setMessage(rs.getString("created_date"));
				 status.setStatusCode("200");
			}
			else {
				status.setStatusCode("2103");
				status.setMessage("Already verified");
				status.setStatus("Failure");
			}
		} catch (Exception e) {
			status.setStatus("500");
			status.setMessage("SQL Exception 358:"+e.getMessage());
			status.setStatus("Failure");
		}
		return status;
	}

	public Status checkUsername(Connection connection, String userName) {
		// TODO Auto-generated method stub
		
		Status status = new Status();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM USER where USER_NAME='"+userName+"'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				status.setMessage("User already exists");
				status.setStatus("Failure");
				status.setStatusCode("200");
			}
			else {
				status.setMessage("User not available");
				status.setStatus("Sucess");
				status.setStatusCode("200");
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setMessage("Something went wrong.."+e.getMessage());
			status.setStatus("Failure");
			status.setStatusCode("500");
		}
		return status;
	}

	public Status forgotUserName(Connection connection, User user) {
		// TODO Auto-generated method stub
		//user name will be find based on the first name and dob
		
		Status status = new Status();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT USER_NAME FROM USER where FIRST_NAME='"+user.getFirstName()+"' AND dob='"+user.getDob()+"'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				status.setMessage(rs.getString("USER_NAME"));
				status.setStatus("Sucess");
				status.setStatusCode("200");
			}
			else {
				status.setMessage("Details are not matched with backend");
				status.setStatus("Failure");
				status.setStatusCode("FU404");
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.setMessage("Something went wrong.."+e.getMessage());
			status.setStatus("Failure");
			status.setStatusCode("500");
		}
		return status;
	}

	public Status forgotPassword(Connection connection, User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
}