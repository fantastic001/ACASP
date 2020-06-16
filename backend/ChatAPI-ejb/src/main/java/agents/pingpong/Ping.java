package agents.pingpong;

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
import com.stefan.data.ACLMessage;
import com.stefan.data.AID;
import com.stefan.data.Agent;
import com.stefan.data.AgentType;
import com.stefan.message.MessageManagerBean;

@Startup
@Singleton
public class Ping implements Agent {

    @PostConstruct
    public void construct() {
        try {
            AgentManager.getInstance().registerAgent(this);
        } catch (AgentExistsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public AID getId() {
        return new AID("", "localhost", new AgentType("ping", "stefan.agents.pingpong"));
    }

    @Override
    public void handleMessage(ACLMessage msg) {
        if (msg.getContent().equals("PONG")) {
            System.out.println("Received reply");
        }
    }

    @EJB
    private MessageManagerBean mmgr;
    
    @Override
    public void handleStart() {
        List<AID> pongAgents = AgentManager.getInstance()
            .getOnlineAgents().stream()
            .filter(agent -> agent.getId().getType().getModule().equals("agents.stefan.pingpong"))
            .map(agent -> agent.getId())
            .collect(Collectors.toList());
        mmgr.post(new ACLMessage(null, getId(), pongAgents, null, "PING", null, null, null, null, null, null, null, null, null, null));
    }

    @Override
    public void handleStop() {
        System.out.println("Stopping ping agent");

    }

    @Override
    public void init() {

    }

    @Override
    public void deinit() {

    }
    
}