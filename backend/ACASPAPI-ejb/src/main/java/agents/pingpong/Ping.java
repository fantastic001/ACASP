package agents.pingpong;

import java.util.List;
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
import com.stefan.message.MessageManagerBean;
import javax.ejb.Local;
import java.lang.Thread;
import javax.ejb.DependsOn;

@Startup
@Singleton
@LocalBean
public class Ping implements Agent {

    @PostConstruct
    public void construct() {
        try {
            AgentManager.getInstance().registerAgent(this);
            // AgentManager.getInstance().login(this);
        }
        catch (AgentExistsException e) {
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
        System.out.println("Getting message");
        if (msg.getContent().equals("PONG")) {
            System.out.println("Received reply");
        }
    }

    @EJB
    private MessageManagerBean mmgr;
    
    @Override
    public void handleStart() {
        List<AID> pongAgents = AgentManager.getInstance()
            .getAllOnlineAgents().stream().map(a -> a.getAgent().getId())
            .collect(Collectors.toList());
        System.out.println("Sending message to pong agents");
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