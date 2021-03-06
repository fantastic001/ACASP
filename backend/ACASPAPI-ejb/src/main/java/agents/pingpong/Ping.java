package agents.pingpong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

import com.stefan.agent.AgentExistsException;
import com.stefan.agent.AgentRunErrorException;
import com.stefan.agent.AgentManager;
import com.stefan.data.ACLMessage;
import com.stefan.data.AID;
import com.stefan.data.Agent;
import com.stefan.data.AgentType;
import com.stefan.message.MessageManager;
import com.stefan.message.MessageManagerBean;
import javax.ejb.Local;
import java.lang.Thread;
import javax.ejb.DependsOn;


@LocalBean
public class Ping implements Agent {


    @Override
    public AID getId() {
        return new AID("", "localhost", new AgentType("ping", "stefan.agents.pingpong"));
    }

    @Override
    public void handleMessage(ACLMessage msg) {
        System.out.println("Getting message");
        if (msg.getContent().equals("PONG")) {
            System.out.println("Received reply");
        }
    }

    private MessageManager mmgr;
    
    @Override
    public void handleStart(Map<String, String[]> params) {
        List<AID> pongAgents = new ArrayList<>();
        pongAgents.add(new AID("", "", new AgentType("pong", "stefan.agents.pingpong")));
        System.out.println("Sending message to pong agents");
        mmgr.post(new ACLMessage(null, getId(), pongAgents, null, "PING", null, null, null, null, null, null, null, null, null, null));
    }

    @Override
    public void handleStop() {
        System.out.println("Stopping ping agent");

    }

    @Override
    public void init(MessageManager msg) {
        this.mmgr = msg;

    }

    @Override
    public void deinit() {

    }
    
}