package com.stefan.data;

import java.io.Serializable;
import java.util.Date;

public class AID implements Serializable{
	


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

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
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof AID)) return false;
		AID other = (AID) obj; 
		return getName().equals(other.getName()) 
		&& getType().getFullName().equals(other.getType().getFullName());
	}
}