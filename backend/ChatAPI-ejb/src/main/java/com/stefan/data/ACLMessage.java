package com.stefan.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ACLMessage {
	
	
	private Performative performative; 
	
	private AID sender; 
	
	private List<AID> receivers; 
	
	private AID replyTo; 
	
	private String content; 
	
	private Object contentObj; 
	
	private HashMap<String, Object> userArgs; 
	
	private String language; 
	
	private String encoding; 
	
	private String orthology; 
	
	private String protocol; 
	
	private String conversationId; 
	
	private String replyWith; 
	
	private String inReplyTo; 
	
	private Long replyBy; 
	
	public ACLMessage() 
	{
	}
	public ACLMessage(Performative _performative, AID _sender, List<AID> _receivers, AID _replyTo, String _content, Object _contentObj, HashMap<String, Object> _userArgs, String _language, String _encoding, String _orthology, String _protocol, String _conversationId, String _replyWith, String _inReplyTo, Long _replyBy) {
		super();
		 
		this.performative = _performative;
		 
		this.sender = _sender;
		 
		this.receivers = _receivers;
		 
		this.replyTo = _replyTo;
		 
		this.content = _content;
		 
		this.contentObj = _contentObj;
		 
		this.userArgs = _userArgs;
		 
		this.language = _language;
		 
		this.encoding = _encoding;
		 
		this.orthology = _orthology;
		 
		this.protocol = _protocol;
		 
		this.conversationId = _conversationId;
		 
		this.replyWith = _replyWith;
		 
		this.inReplyTo = _inReplyTo;
		 
		this.replyBy = _replyBy;
		
	}
	
	 
	public Performative getPerformative() 
	{
		return this.performative;
	}

	public void setPerformative(Performative newValue) 
	{
		this.performative = newValue;
	}
	 
	public AID getSender() 
	{
		return this.sender;
	}

	public void setSender(AID newValue) 
	{
		this.sender = newValue;
	}
	 
	public List<AID> getReceivers() 
	{
		return this.receivers;
	}

	public void setReceivers(List<AID> newValue) 
	{
		this.receivers = newValue;
	}
	 
	public AID getReplyTo() 
	{
		return this.replyTo;
	}

	public void setReplyTo(AID newValue) 
	{
		this.replyTo = newValue;
	}
	 
	public String getContent() 
	{
		return this.content;
	}

	public void setContent(String newValue) 
	{
		this.content = newValue;
	}
	 
	public Object getContentObj() 
	{
		return this.contentObj;
	}

	public void setContentObj(Object newValue) 
	{
		this.contentObj = newValue;
	}
	 
	public HashMap<String, Object> getUserArgs() 
	{
		return this.userArgs;
	}

	public void setUserArgs(HashMap<String, Object> newValue) 
	{
		this.userArgs = newValue;
	}
	 
	public String getLanguage() 
	{
		return this.language;
	}

	public void setLanguage(String newValue) 
	{
		this.language = newValue;
	}
	 
	public String getEncoding() 
	{
		return this.encoding;
	}

	public void setEncoding(String newValue) 
	{
		this.encoding = newValue;
	}
	 
	public String getOrthology() 
	{
		return this.orthology;
	}

	public void setOrthology(String newValue) 
	{
		this.orthology = newValue;
	}
	 
	public String getProtocol() 
	{
		return this.protocol;
	}

	public void setProtocol(String newValue) 
	{
		this.protocol = newValue;
	}
	 
	public String getConversationId() 
	{
		return this.conversationId;
	}

	public void setConversationId(String newValue) 
	{
		this.conversationId = newValue;
	}
	 
	public String getReplyWith() 
	{
		return this.replyWith;
	}

	public void setReplyWith(String newValue) 
	{
		this.replyWith = newValue;
	}
	 
	public String getInReplyTo() 
	{
		return this.inReplyTo;
	}

	public void setInReplyTo(String newValue) 
	{
		this.inReplyTo = newValue;
	}
	 
	public Long getReplyBy() 
	{
		return this.replyBy;
	}

	public void setReplyBy(Long newValue) 
	{
		this.replyBy = newValue;
	}
	
}