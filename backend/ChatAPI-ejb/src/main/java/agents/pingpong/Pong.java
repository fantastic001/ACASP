package agents.pingpong;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
import com.stefan.message.MessageManagerBean;

@Startup
@Singleton
public class Pong implements Agent {

    @PostConstruct
    public void construct() {
        try {
            AgentManager.getInstance().registerAgent(this);
            AgentManager.getInstance().login(this);
            System.out.println("Starting all ping agents");
            AgentManager.getInstance().getAgents().stream()
                .filter(agent -> agent.getId().getType().getName().equals("ping"))
                .forEach(agent -> {
                        try {
                            AgentManager.getInstance().login(agent);
                        } catch (AgentRunErrorException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                });
                
        } catch (AgentExistsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AgentRunErrorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

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

    @EJB
    private MessageManagerBean mmgr;
    
    @Override
    public void handleStart() {
        System.out.println("Starting pong agent");
    }

    @Override
    public void handleStop() {
        System.out.println("Stopping pong agent");

    }

    @Override
    public void init() {

    }

    @Override
    public void deinit() {

    }
    
}