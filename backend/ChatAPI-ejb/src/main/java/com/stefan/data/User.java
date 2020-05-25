package com.stefan.data;

import java.util.Date;

public class User {
	
	
	private String username; 
	
	private String password; 
	
	private String hostAlias; 
	
	public User() 
	{
	}
	public User(String _username, String _password, String _hostAlias) {
		super();
		 
		this.username = _username;
		 
		this.password = _password;
		 
		this.hostAlias = _hostAlias;
		
	}
	
	 
	public String getUsername() 
	{
		return this.username;
	}

	public void setUsername(String newValue) 
	{
		this.username = newValue;
	}
	 
	public String getPassword() 
	{
		return this.password;
	}

	public void setPassword(String newValue) 
	{
		this.password = newValue;
	}
	 
	public String getHostAlias() 
	{
		return this.hostAlias;
	}

	public void setHostAlias(String newValue) 
	{
		this.hostAlias = newValue;
	}
	
}