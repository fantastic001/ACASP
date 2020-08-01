package com.stefan.data;

public class RunningAgent {
    
    private Agent agent; 
    private String name;

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

}