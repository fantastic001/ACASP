package com.stefan.agent;

import com.stefan.data.Agent;

public interface LoginListener {
    public void agentLoggedIn(Agent agent);
    public void agentLoggedOut(Agent agent);
}
