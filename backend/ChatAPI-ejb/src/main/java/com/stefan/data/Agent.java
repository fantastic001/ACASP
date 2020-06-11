package com.stefan.data;

import javax.ejb.Stateful;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;

@Stateful
public interface Control {
	public AID getId();
	public void handleMessage(ACLMessage msg);
}
