package com.omni.curis.entity;

public class ProductOrder {
	
	private int[]   listofitems;
	private String  name;
	private String  emailId;
	private String  mobileno;
	private String  address;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getListofitems() {
		return listofitems;
	}
	public void setListofitems(int[] listofitems) {
		this.listofitems = listofitems;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	

}
