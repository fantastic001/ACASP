package agents.acasp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
public class Fetcher implements Agent {
    
    private MessageManager messageManager;

    private Thread crawlerThread;

    private String id; 

    private String url; 

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
        return new AID(id, "localhost", new AgentType("fetcher", "stefan.agents.acasp"));
    }

    @Override
    public void handleMessage(ACLMessage msg) {
        ArrayList<AID> masters = new ArrayList<>();
        masters.add(new AID("", "", new AgentType("master", "stefan.agents.acasp")));
        if (! msg.getSender().getType().getFullName().equals("stefan.agents.acasp.master")) {
            return;
        }
        switch (msg.getPerformative()) {
            case START: // master sends initial request
                
                break;
            case ACCEPT: // contractor is willing to fullfil request 
                break;
            case SUCCESS: // task done 
                break; 
            case ERROR: // task is not done, choose new contractor 
                break;
			case DEFAULT:
				break;
			case REJECT:
				break;
            case REQUEST:
                if (crawlerThread != null) {
                    messageManager.post(new ACLMessage(
                        Performative.ACCEPT, 
                        this.getId(), 
                        masters, 
                        null, 
                        id,
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
                else {
                    messageManager.post(new ACLMessage(
                        Performative.REJECT, 
                        this.getId(), 
                        masters, 
                        null, 
                        id, 
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
			default:
				break;

        }
    }

    @EJB
    private MessageManagerBean mmgr;
    
    @Override
    public void handleStart(Map<String, String[]> params) {
        crawlerThread = null;
        url = params.getOrDefault("site", new String[] {"www.google.com"})[0];
        System.out.println("Starting fetcher for site: " + url);
        crawlerThread = new Thread(new Crawler(url, "stan"));
        crawlerThread.start();
    }

    @Override
    public void handleStop() {
        if (crawlerThread != null) crawlerThread.stop();
    }

    @Override
    public void init(MessageManager messageManager) {

        this.messageManager = messageManager;
    }

    @Override
    public void deinit() {

    }
}