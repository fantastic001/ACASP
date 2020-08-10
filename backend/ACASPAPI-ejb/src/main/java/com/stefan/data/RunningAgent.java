package com.stefan.data;

public class RunningAgent {
    
    private Agent agent; 
    private String name;
    private String nodeAlias;

    public RunningAgent() {
        this.name = "";
        this.agent = null;
        this.nodeAlias = "";
    }

    public RunningAgent(String name, Agent agent) {
        this.agent = agent;
        this.name = name;
    }

    public Agent getAgent() {
        return agent;
    }
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getNodeAlias() {
        return this.nodeAlias;
    }

    public void setNodeAlias(String alias) {
        this.nodeAlias = alias;
    }

}