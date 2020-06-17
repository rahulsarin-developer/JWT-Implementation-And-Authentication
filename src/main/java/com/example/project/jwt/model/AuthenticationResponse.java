package com.example.project.jwt.model;

public class AuthenticationResponse {
	
	private final String userName;
	
	private final String jwt;
	
	public AuthenticationResponse(final String userName, final String jwt) {
		this.jwt = jwt;
		this.userName = userName;
	}
	
	public String getJwt() {
		return jwt;
	}
	
	public String getUserName() {
		return userName;
	}
}