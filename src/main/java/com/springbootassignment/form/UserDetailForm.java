package com.springbootassignment.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDetailForm {

	private long userId;

	@NotNull(message = "User name should not be empty")
	private String userName;

	private String city;

	@NotEmpty(message = "Password should not be empty")
	private String password;

	public UserDetailForm() {

	}

	public UserDetailForm(String userName, String city, String password) {
		this.userName = userName;
		this.city = city;
		this.password = password;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDetail [userId=" + userId + ", userName=" + userName + ", city=" + city + ", password=" + password + "]";
	}

}