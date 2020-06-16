package com.stefan.data;

import java.util.Date;

public class AID {
	
	
	private String name; 
	
	private String hostAlias; 
	
	private AgentType type; 
	
	public AID() 
	{
	}
	public AID(String _name, String _hostAlias, AgentType _type) {
		super();
		 
		this.name = _name;
		 
		this.hostAlias = _hostAlias;
		 
		this.type = _type;
		
	}
	
	 
	public String getName() 
	{
		return this.name;
	}

	public void setName(String newValue) 
	{
		this.name = newValue;
	}
	 
	public String getHostAlias() 
	{
		return this.hostAlias;
	}

	public void setHostAlias(String newValue) 
	{
		this.hostAlias = newValue;
	}
	 
	public AgentType getType() 
	{
		return this.type;
	}

	public void setType(AgentType newValue) 
	{
		this.type = newValue;
	}
	
}