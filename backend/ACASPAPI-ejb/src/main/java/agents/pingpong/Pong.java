package agents.pingpong;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

import com.stefan.agent.AgentExistsException;
import com.stefan.agent.AgentManager;
import com.stefan.agent.AgentRunErrorException;
import com.stefan.data.ACLMessage;
import com.stefan.data.AID;
import com.stefan.data.Agent;
import com.stefan.data.AgentType;
import com.stefan.message.MessageManager;
import com.stefan.message.MessageManagerBean;

@LocalBean
public class Pong implements Agent {



    @Override
    public AID getId() {
        return new AID("", "localhost", new AgentType("pong", "stefan.agents.pingpong"));
    }

    @Override
    public void handleMessage(ACLMessage msg) {
        if (msg.getContent().equals("PING")) {
            System.out.println("Received ping");
            mmgr.post(new ACLMessage(null, getId(), Collections.singletonList(msg.getSender()), null, "PONG", null, null, null, null, null, null, null, null, null, null));
        }
    }

    private MessageManager mmgr;
    
    @Override
    public void handleStart(Map<String, String[]> params) {
        System.out.println("Starting pong agent");
    }

    @Override
    public void handleStop() {
        System.out.println("Stopping pong agent");

    }

    @Override
    public void init(MessageManager msg) {
        this.mmgr = msg;
    }

    @Override
    public void deinit() {

    }
    
}