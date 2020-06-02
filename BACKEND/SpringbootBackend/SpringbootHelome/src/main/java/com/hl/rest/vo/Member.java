package com.hl.rest.vo;

public class Member {
	private String username, school, email, password, isteacher, grade, classnum;
	
	public Member() {}
	public Member(String username, String school, String email, String password, String isteacher, String grade,
			String classnum) {
		super();
		this.username = username;
		this.school = school;
		this.email = email;
		this.password = password;
		this.isteacher = isteacher;
		this.grade = grade;
		this.classnum = classnum;
	}

	public String getIsteacher() {
		return isteacher;
	}
	public void setIsteacher(String isteacher) {
		this.isteacher = isteacher;
	}
	public String getClassnum() {
		return classnum;
	}
	public void setClassnum(String classnum) {
		this.classnum = classnum;
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
