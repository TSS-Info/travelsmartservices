package com.tis.savemytime.models;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7374997149569295773L;

	private int userID;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String passwordCreateDate;
	private int cityID;
	private String districtVillageName;
	private String timeStamp;
	private String primaryLanguageID;
	private int addressID;
	private int identityID;
	private int cardID;
	private String emailLink;
	private String mobileNo;
	private String dob;
	
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailLink() {
		return emailLink;
	}
	public void setEmailLink(String emailLink) {
		this.emailLink = emailLink;
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordCreateDate() {
		return passwordCreateDate;
	}
	public void setPasswordCreateDate(String passwordCreateDate) {
		this.passwordCreateDate = passwordCreateDate;
	}
	public int getCityID() {
		return cityID;
	}
	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	public String getDistrictVillageName() {
		return districtVillageName;
	}
	public void setDistrictVillageName(String districtVillageName) {
		this.districtVillageName = districtVillageName;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getPrimaryLanguageID() {
		return primaryLanguageID;
	}
	public void setPrimaryLanguageID(String primaryLanguageID) {
		this.primaryLanguageID = primaryLanguageID;
	}
	public int getAddressID() {
		return addressID;
	}
	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}
	public int getIdentityID() {
		return identityID;
	}
	public void setIdentityID(int identityID) {
		this.identityID = identityID;
	}
	public int getCardID() {
		return cardID;
	}
	public void setCardID(int cardID) {
		this.cardID = cardID;
	}
	
	
}
