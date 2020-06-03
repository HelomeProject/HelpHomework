package com.hl.rest.vo;

import javax.validation.constraints.NotNull;

public class Token {
	@NotNull private String token;
	
	public Token() {}
	public Token(@NotNull String token) {
		this.token = token;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "Token [token=" + token + "]";
	}
}
