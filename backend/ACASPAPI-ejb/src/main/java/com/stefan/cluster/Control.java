package com.stefan.cluster;

import javax.ejb.Startup;
import javax.enterprise.inject.spi.CDI;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.stefan.agent.AgentExistsException;
import com.stefan.agent.AgentManager;
import com.stefan.data.Agent;

import javax.ejb.Singleton;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;

@Singleton
@Startup
public class Control {

    @Schedule(hour = "*", minute = "*", second = "*/15")
    public void ping() {
        getControl().onPing();
    }

    private ControlInterface node = null;

    public ControlInterface getControl() {
        if (node != null)
            return node;
        if (isMaster()) {
            node = new MasterNode();
            node.init();
        } else {
            node = new WorkerNode();
            node.init();
        }
        return node;
    }

    @PostConstruct
    private void run() {
        Collection<String> classes = new ResourceReader().getAgentClasses();
        for (String aClass : classes) {
            try {
                System.out.println("Finding class " + aClass);
                Context ctx = new InitialContext();
                AgentManager.getInstance().registerAgent( ctx.lookup(aClass));
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (AgentExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        node = getControl();
    }

    private boolean isMaster() {
        ResourceReader reader = new ResourceReader();
        if (reader.getProperty("masterHostname", "").equals("")) {
            return true;
        }
        else return false;
    }

}
