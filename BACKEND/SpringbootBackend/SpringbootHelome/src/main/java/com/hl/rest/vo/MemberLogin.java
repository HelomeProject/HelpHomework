package com.hl.rest.vo;

public class MemberLogin {
	private String email, password;
	
	public MemberLogin(){}
	public MemberLogin(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "MemberLogin [email=" + email + ", password=" + password + "]";
	}
}
