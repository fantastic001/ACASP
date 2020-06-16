package com.stefan;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.stefan.agent.LoginListener;
import com.stefan.agent.AgentManager;
import com.stefan.data.Agent;

@ServerEndpoint("/websocket/login")
public class AgentWebSocket implements LoginListener {
    private Session session;
    // TODO agent adding 
    // Agent deleton
    // message events
    // agent type update

    @Override
    public void agentLoggedIn(Agent agent) {
        session.getAsyncRemote().sendText("LOGIN " + agent.getId());
    }
    @Override
    public void agentLoggedOut(Agent agent) {
        // session.getAsyncRemote().sendText("LOGOUT " + user.getUsername());
    }
    
    @OnMessage
    public String sayHello(String name) {
        System.out.println("Say hello to '" + name + "'");
        return ("Hello " + name + " from websocket endpoint");
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("WebSocket opened: " + session.getId());
        AgentManager.getInstance().addLoginListener(this);
    }

    @OnClose
    public void helloOnClose(CloseReason reason) {
        System.out.println("WebSocket connection closed with CloseCode: " + reason.getCloseCode());
    }
}
