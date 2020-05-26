package com.hl.rest.vo;

public class Member {
	private String username, school, email, password, grade;
	
	public Member() {}
	public Member(String username, String school, String email, String password, String grade) {
		this.username = username;
		this.school = school;
		this.email = email;
		this.password = password;
		this.grade = grade;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Member [username=" + username + ", school=" + school + ", email=" + email + ", password=" + password
				+ ", grade=" + grade + "]";
	}
		
}
