package com.stefan.data;

import java.util.Date;

public class Host {
	
	
	private String alias; 
	
	private String address; 
	
	public Host() 
	{
	}
	public Host(String _alias, String _address) {
		super();
		 
		this.alias = _alias;
		 
		this.address = _address;
		
	}
	
	 
	public String getAlias() 
	{
		return this.alias;
	}

	public void setAlias(String newValue) 
	{
		this.alias = newValue;
	}
	 
	public String getAddress() 
	{
		return this.address;
	}

	public void setAddress(String newValue) 
	{
		this.address = newValue;
	}
	
}