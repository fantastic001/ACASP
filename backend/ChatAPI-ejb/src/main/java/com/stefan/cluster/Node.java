package com.stefan.cluster;

import java.util.Date;
import java.util.concurrent.Future;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class Node {
	
	private boolean isMaster;
	
	private String alias; 
	
	private String hostname; 
	
	private int port; 
	
	private String path; 
	
	private int state; // -=OK, 1=CRITICAL, 2=DOWN

	public Node() 
	{
		this.state = 0; // OK
	}

	public Node(String _alias, String _hostname, int _port, String _path) {
		super();
		 
		this.alias = _alias;
		 
		this.hostname = _hostname;
		 
		this.port = _port;
		 
		this.path = _path;
		this.isMaster = false;
		
	}
	public Node(String masterAddress) {
		this.isMaster = true;
		this.hostname = masterAddress;
	}
	
	public String getAddress() {
		return isMaster
			? hostname 
			: "http://" + getHostname() + ":" + getPort() + "/" + getPath();
	}
	
	public <R> Response post(String location, R body) {
		final String path = getAddress() + location; 
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(UriBuilder.fromPath(path));
        System.out.println("Preparing request for sending to " + path);
        return target.request().post(Entity.entity(body, "application/json"));
	}

	public <R> Future<Response> postAsync(String location, R body) {
		final String path = getAddress() + location; 
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(UriBuilder.fromPath(path));
        System.out.println("Preparing request for sending to " + path);
        return target.request().async().post(Entity.entity(body, "application/json"));
	}
	public <R> Future<Response> getAsync(String location) {
		final String path = getAddress() + location; 
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(UriBuilder.fromPath(path));
        System.out.println("Preparing request for sending to " + path);
        return target.request().async().get();
	}
	public <R> Future<Response> deleteAsync(String location) {
		final String path = getAddress() + location; 
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(UriBuilder.fromPath(path));
        System.out.println("Preparing request for sending to " + path);
        return target.request().async().delete();
	}

	public String getAlias() 
	{
		return this.alias;
	}

	public void setAlias(String newValue) 
	{
		this.alias = newValue;
	}
	 
	public String getHostname() 
	{
		return this.hostname;
	}

	public void setHostname(String newValue) 
	{
		this.hostname = newValue;
	}
	 
	public int getPort() 
	{
		return this.port;
	}

	public void setPort(int newValue) 
	{
		this.port = newValue;
	}
	 
	public String getPath() 
	{
		return this.path;
	}

	public void setPath(String newValue) 
	{
		this.path = newValue;
	}

	public void setState(int state) {
		this.state = state; 
	}

	public int getState() {
		return this.state;
	}
	
}