package com.stefan.data;

import java.time.LocalDateTime;

public class Message {
	
	
	private String fromUsername; 
	
	private String toUsername; 
	
	private LocalDateTime creationTime; 
	
	private String subject; 
	
	private String text; 
	
	public Message() 
	{
	}
	public Message(String _fromUsername, String _toUsername, LocalDateTime _creationTime, String _subject, String _text) {
		super();
		 
		this.fromUsername = _fromUsername;
		 
		this.toUsername = _toUsername;
		 
		this.creationTime = _creationTime;
		 
		this.subject = _subject;
		 
		this.text = _text;
		
	}
	
	 
	public String getFromUsername() 
	{
		return this.fromUsername;
	}

	public void setFromUsername(String newValue) 
	{
		this.fromUsername = newValue;
	}
	 
	public String getToUsername() 
	{
		return this.toUsername;
	}

	public void setToUsername(String newValue) 
	{
		this.toUsername = newValue;
	}
	 
	public LocalDateTime getCreationTime() 
	{
		return this.creationTime;
	}

	public void setCreationTime(LocalDateTime newValue) 
	{
		this.creationTime = newValue;
	}
	 
	public String getSubject() 
	{
		return this.subject;
	}

	public void setSubject(String newValue) 
	{
		this.subject = newValue;
	}
	 
	public String getText() 
	{
		return this.text;
	}

	public void setText(String newValue) 
	{
		this.text = newValue;
	}
	
}