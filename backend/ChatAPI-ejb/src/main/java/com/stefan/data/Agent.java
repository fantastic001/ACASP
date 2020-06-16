package com.stefan.data;

import javax.ejb.Stateful;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;

@Stateful
public interface Agent {
	public AID getId();
	public void handleMessage(ACLMessage msg);
	public void handleStart();
	public void handleStop();
	public void init();
	public void deinit();
}
