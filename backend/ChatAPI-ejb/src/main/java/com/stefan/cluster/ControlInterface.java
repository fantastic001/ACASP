package com.stefan.cluster;

import com.stefan.data.User;

import java.util.Collection;

public interface ControlInterface {
    // https://www.baeldung.com/resteasy-client-tutorial

    public void init();
    public void run();
    public void finish();
    public void nodeAdded(Node node);
    public void nodeRemoved(String alias);
    public Collection<User> getAllUsers();
    public void onPing();
    public void onPong(Node node);
    
    public boolean hasUser(User user);
    public void setUsers(Collection<User> users);

    public Node findNode(String alias);

    public void login(Collection<User> user);
    public void register(User user);
}