package com.stefan.cluster;

import java.util.Collection;
import com.stefan.data.User;
import com.stefan.user.UserManager;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.Random;
import java.util.Collection;
import java.util.ArrayList;

public class WorkerNode implements ControlInterface {
    
    private Collection<Node> nodes;

    private String randomNodeAlias() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
            (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();   
        return generatedString;
    }

    private <T> Response postToMaster(String location, T body) {
        ResourceReader reader = new ResourceReader();
        String masterHostname = reader.getProperty("masterHostname", "");
        final String path = masterHostname + location;
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(UriBuilder.fromPath(path));
        Response res = target.request().post(Entity.entity(body, "application/json"));
        return res;
    }

    private Node node;
    @Override
    public void init() {
        nodes = new ArrayList<>();
        ResourceReader reader = new ResourceReader();
        String masterHostname = reader.getProperty("masterHostname", "");
        System.out.println("Running in worker node with master: " + masterHostname);
        final String path = masterHostname + "/register"; 
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(UriBuilder.fromPath(path));
        System.out.println("Preparing request for registration...");
        String alias = reader.getProperty("NODE_ALIAS", "node-" + randomNodeAlias());
        System.out.println("Alias: " + alias);
        node = new Node(
            alias,
            reader.getProperty("NODE_HOSTNAME", System.getenv("HOSTNAME")), 
            Integer.parseInt(reader.getProperty("NODE_PORT", "8080")), 
            reader.getProperty("NODE_PATH", "/ChatAPI-web/rest")
        );
        System.out.println("Sending POST request to master to URL: " + path);

        Response res = target.request().post(Entity.entity(node, "application/json"));
        System.out.println("Node registration response: " + res.getStatus());
        // RegisterEndpoint proxy = target.proxy(RegisterEndpoint.class);
        // proxy.register(new Node("node", "hostname", 80, "/"));
        // get list of all users from master and add them to UserManager 
    }

    @Override
    public void run() {

    }

    @Override
    public void finish() 
    {
        //  send notification to master of deinitializing node 
    }

    @Override
    public void nodeAdded(Node node) {
        System.out.println("Got notification about new node with alias " + node.getAlias());
        nodes.add(node);
    }
    
    @Override
    public void nodeRemoved(String alias) {
        Node node = null; 
        for (Node n : nodes) {
            if (n.getAlias().equals(alias)) node = n;
        }
        if (node != null) {
            System.out.println("Removing node from registered nodes: " + node.getAlias());
            nodes.remove(node);
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        return UserManager.getInstance().getUsers();
    }
    @Override
    public void onPing() {

    }

    @Override
    public void onPong(Node node) {
        
    }

    @Override
    public boolean hasUser(User u) {
        User user = null;
        for (User usr : UserManager.getInstance().getUsers()) {
            if (usr.getUsername().equals(u.getUsername())) user = usr;
        }
        if (user == null) return false;
        if (user.getHostAlias() == null) return false; // legacy
        return user.getHostAlias().equals(node.getAlias());
    }

    @Override
    public void setUsers(Collection<User> users) {
    }


    @Override
    public Node findNode(String alias) {
        if (alias.equals(node.getAlias())) return node;
        for (Node node : nodes) {
            if (node.getAlias().equals(alias)) return node;
        }
        ResourceReader reader = new ResourceReader();
        String masterHostname = reader.getProperty("masterHostname", "");
        return new Node(masterHostname);
    }

    @Override
    public void login(Collection<User> users) {
        for (Node node : nodes) {
            node.postAsync("/users/loggedIn/", users);
        }
        postToMaster("/users/loggedIn", users);
    }

    @Override
    public void register(User user) {
        if (user.getHostAlias() != null) return;
        user.setHostAlias(node.getAlias());
        for (Node node : nodes) {
            node.postAsync("/users/register/", user);
        }
        postToMaster("/users/register/", user);
    }
}