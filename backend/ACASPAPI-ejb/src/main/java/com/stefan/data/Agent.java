package com.stefan.data;

import javax.ejb.Stateful;

import com.stefan.message.MessageManager;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;

@Stateful
public interface Agent {
	public AID getId();
	public void handleMessage(ACLMessage msg);
	public void handleStart();
	public void handleStop();
	public void init(MessageManager messageManager);
	public void deinit();
}
