package com.stefan.cluster;

import java.util.Collection;

import com.stefan.agent.AgentManager;
import com.stefan.data.ACLMessage;
import com.stefan.data.Agent;
import com.stefan.data.RunningAgent;
import com.stefan.message.MessageManager;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ejb.EJB;
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
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
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
        final String path = masterHostname + "/node";

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(UriBuilder.fromPath(path));
        // System.out.println("Preparing request for registration...");
        String alias = reader.getProperty("NODE_ALIAS", "node-" + randomNodeAlias());
        // System.out.println("Alias: " + alias);
        node = new Node(alias, reader.getProperty("NODE_HOSTNAME", System.getenv("HOSTNAME")),
                Integer.parseInt(reader.getProperty("NODE_PORT", "8080")),
                reader.getProperty("NODE_PATH", "/ACASPAPI-web/rest"));
        // System.out.println("Sending POST request to master to URL: " + path);

        Response res = target.request().post(Entity.entity(node, "application/json"));
        // System.out.println("Node registration response: " + res.getStatus());
        // RegisterEndpoint proxy = target.proxy(RegisterEndpoint.class);
        // proxy.register(new Node("node", "hostname", 80, "/"));
        // get list of all users from master and add them to UserManager
    }

    @Override
    public void run() {

    }

    @Override
    public void finish() {
        // send notification to master of deinitializing node
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
            if (n.getAlias().equals(alias))
                node = n;
        }
        if (node != null) {
            System.out.println("Removing node from registered nodes: " + node.getAlias());
            nodes.remove(node);
        }
    }

    @Override
    public void onPing() {

    }

    @Override
    public void onPong(Node node) {

    }

    @Override
    public Node findNode(String alias) {
        if (alias.equals(node.getAlias()))
            return node;
        for (Node node : nodes) {
            if (node.getAlias().equals(alias))
                return node;
        }
        ResourceReader reader = new ResourceReader();
        String masterHostname = reader.getProperty("masterHostname", "");
        return new Node(masterHostname);
    }

    @Override
    public Collection<Agent> getAllAgents() {
        return AgentManager.getInstance().getAgents();
    }

    @Override
    public Collection<RunningAgent> getRunningAgents() {
        return AgentManager.getInstance().getOnlineAgents();
    }

    @Override
    public void setAgents(Collection<Agent> agents) {
        // TODO Auto-generated method stub

    }

    @Override
    public void runAgent(RunningAgent agent) {
        agent.setNodeAlias(node.getAlias());
        postToMaster("/agents/running", AgentManager.getInstance().getAllOnlineAgents());
    }
    public boolean postMessage(ACLMessage message) {
        if (message.getInReplyTo() == null) {
            // System.out.println("Message not from master, sending to master");
            message.setInReplyTo(node.getAlias());
            postToMaster("/messages/", message);       
            return false;
        }
        if (!message.getInReplyTo().equals("master")) 
        {
            // System.out.println("Message not from master, sending to master");
            message.setInReplyTo(node.getAlias());
            postToMaster("/messages/", message);       
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void setRunningAgents(Collection<RunningAgent> agents) {
        AgentManager.getInstance().setAllOnlineAgents(agents);
    }
    public void agentRemoved(String name) {
        for (Node n : nodes) {
            n.postAsync("/agents/running", AgentManager.getInstance().getAllOnlineAgents());
        }
        postToMaster("/agents/running", AgentManager.getInstance().getAllOnlineAgents());
    }

}