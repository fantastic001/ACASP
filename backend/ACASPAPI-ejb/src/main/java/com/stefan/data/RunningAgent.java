package com.stefan.data;

public class RunningAgent {
    
    private Agent agent; 
    private String name;

    public RunningAgent() {
        this.name = "";
        this.agent = null;
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

}