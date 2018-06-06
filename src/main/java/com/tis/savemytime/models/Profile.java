package com.tis.savemytime.models;

import java.io.Serializable;

public class Profile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9206997684702304739L;
	
	private int userID;
	private String emailID;
	private String mobilePhone;
	private String primaryLanguageID;
	private String companyName;
	private String companyWebAddress;
	private String cityID;
	private String districtVillageValue;
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getPrimaryLanguageID() {
		return primaryLanguageID;
	}
	public void setPrimaryLanguageID(String primaryLanguageID) {
		this.primaryLanguageID = primaryLanguageID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyWebAddress() {
		return companyWebAddress;
	}
	public void setCompanyWebAddress(String companyWebAddress) {
		this.companyWebAddress = companyWebAddress;
	}
	public String getCityID() {
		return cityID;
	}
	public void setCityID(String cityID) {
		this.cityID = cityID;
	}
	public String getDistrictVillageValue() {
		return districtVillageValue;
	}
	public void setDistrictVillageValue(String districtVillageValue) {
		this.districtVillageValue = districtVillageValue;
	}
	
	
}
