package agents.contractnet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
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
import com.stefan.data.Performative;
import com.stefan.message.MessageManager;
import com.stefan.message.MessageManagerBean;
import javax.ejb.Local;
import java.lang.Thread;
import javax.ejb.DependsOn;

@Startup
@Singleton
@LocalBean
public class Contractor implements Agent {


    private String getRandomAgentName() {
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
		return "Agent-" + generatedString;
    }
    
    @EJB
    private MessageManager messageManager;

    private String id;

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
        return new AID(id, "localhost", new AgentType("contractor", "stefan.agents.contractnet"));
    }

    @Override
    public void handleMessage(ACLMessage msg) {
        switch (msg.getPerformative()) {
            case ACCEPT:
                if (msg.getContent().equals(getId().getName())) {
                    System.out.println("CONTRACTOR-" + getId().getName() + " got task");
                    ArrayList<AID> receivers = new ArrayList<>();
                    receivers.add(msg.getSender());
                    messageManager.post(new ACLMessage(
                        Performative.SUCCESS,
                        this.getId(), 
                        receivers,
                        null,
                        "",
                        null, 
                        null, 
                        null, 
                        null, 
                        null, 
                        null, 
                        null, 
                        null, 
                        null, 
                        null
                    ));
                }
                break;
            case DEFAULT:
                break;
            case ERROR:
                break;
            case REJECT:
                if (! msg.getContent().equals(getId().getName())) {
                    System.out.println("AGENT " + getId().getName() + " not selected");
                }
                break;
            case REQUEST:
                System.out.println("CONTRACTOR-" + getId().getName() + " got request");
                ArrayList<AID> receivers = new ArrayList<>();
                receivers.add(msg.getSender());
                messageManager.post(new ACLMessage(
                    Performative.ACCEPT, 
                    this.getId(), 
                    receivers,
                    null,
                    "",
                    null, 
                    null, 
                    null, 
                    null, 
                    null, 
                    null, 
                    null, 
                    null, 
                    null, 
                    null
                ));
                break;
            case START:
                break;
            case SUCCESS:
                break;
            default:
                break;
            
        }
    }

    @EJB
    private MessageManagerBean mmgr;
    
    @Override
    public void handleStart() {
        id = getRandomAgentName();
    }

    @Override
    public void handleStop() {

    }

    @Override
    public void init() {

    }

    @Override
    public void deinit() {

    }
}