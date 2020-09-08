package agents.contractnet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ws.rs.BeanParam;

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
public class Manager implements Agent {
    
    private String id; 
    private ArrayList<AID> contractors; 

    @EJB
    private MessageManager messageManager;

    @PostConstruct
    public void construct() {
        try {
            this.id = getRandomAgentName();
            AgentManager.getInstance().registerAgent(this);
        }
        catch (AgentExistsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

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

    @Override
    public AID getId() {
        return new AID(id, "localhost", new AgentType("manager", "stefan.agents.contractnet"));
    }

    private AID chooseOneContractor() {
        if (findAllAvailableContractors().size() == contractors.size()) {
            return contractors.stream().findFirst().orElse(null);
        }
        return null;
    }

    private Collection<AID> findAllAvailableContractors() {
        return AgentManager.getInstance().getAllOnlineAgents().stream()
            .map(x -> x.getId())
            .filter(x -> x.getType().getFullName().equals("stefan.agents.contractnet.contractor"))
            .collect(Collectors.toList());
    }

    private void request() {
        contractors = new ArrayList<>();
        System.out.println("Contractors:");
        for (AID c : findAllAvailableContractors()) {
            System.out.println(c.getName());
        }
        messageManager.post(new ACLMessage(
            Performative.REQUEST, 
            this.getId(), 
            findAllAvailableContractors().stream().collect(Collectors.toList()),
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

    @Override
    public void handleMessage(ACLMessage msg) {
        if (! msg.getSender().getType().getFullName().equals("stefan.agents.contractnet.contractor")) {
            return;
        }
        switch (msg.getPerformative()) {
            case START: // manager sends initial request
                request();
                break;
            case ACCEPT: // contractor is willing to fullfil request 
                contractors.add(msg.getSender());
                AID contractor = chooseOneContractor();
                if (contractor != null) {
                    System.out.println("MANAGER" + " got task to " + contractor.getName());
                    ArrayList<AID> receivers = new ArrayList<>();
                    receivers.add(contractor);
                    messageManager.post(new ACLMessage(
                        Performative.ACCEPT, 
                        this.getId(), 
                        receivers, 
                        null, 
                        contractor.getName(), 
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
                    contractors.remove(contractor);
                    for (AID c : contractors) {
                        System.out.println("MANAGER: rejecting " + c.getName());
                    }
                    messageManager.post(new ACLMessage(
                        Performative.REJECT, 
                        this.getId(), 
                        contractors, 
                        null, 
                        contractor.getName(), 
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
            case SUCCESS: // task done 
                System.out.println("SUCCESS: Contractor " + msg.getSender().getName());
                break; 
            case ERROR: // task is not done, choose new contractor 
                System.out.println("ERROR: Contractor " + msg.getSender().getName());
                break;
			case DEFAULT:
				break;
			case REJECT:
				break;
			case REQUEST:
				break;
			default:
				break;

        }
    }

    @EJB
    private MessageManagerBean mmgr;
    
    @Override
    public void handleStart() {
        request();
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