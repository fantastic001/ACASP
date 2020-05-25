package com.stefan;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.stefan.data.User; 
import com.stefan.user.LoginListener;
import com.stefan.user.UserManager;

@ServerEndpoint("/websocket/login")
public class UserWebSocket implements LoginListener {
    private Session session;

    @Override
    public void userLoggedIn(User user) {
        session.getAsyncRemote().sendText("LOGIN " + user.getUsername());
    }
    @Override
    public void userLoggedOut(User user) {
        session.getAsyncRemote().sendText("LOGOUT " + user.getUsername());
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
        UserManager.getInstance().addLoginListener(this);
    }

    @OnClose
    public void helloOnClose(CloseReason reason) {
        System.out.println("WebSocket connection closed with CloseCode: " + reason.getCloseCode());
    }
}
