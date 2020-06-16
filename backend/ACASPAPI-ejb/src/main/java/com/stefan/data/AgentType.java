package com.stefan.data;

import java.util.Date;

public class AgentType {
	
	
	private String name; 
	
	private String module; 
	
	public AgentType() 
	{
	}
	public AgentType(String _name, String _module) {
		super();
		 
		this.name = _name;
		 
		this.module = _module;
		
	}
	
	 
	public String getName() 
	{
		return this.name;
	}

	public void setName(String newValue) 
	{
		this.name = newValue;
	}
	 
	public String getModule() 
	{
		return this.module;
	}

	public void setModule(String newValue) 
	{
		this.module = newValue;
	}

	public String getFullName() {
		return getModule() + "."+getName();
	}
	
}