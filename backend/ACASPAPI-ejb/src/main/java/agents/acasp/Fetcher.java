package agents.acasp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class Fetcher implements Agent, FoundPageListener {
    
    private MessageManager messageManager;
    private Thread crawlerThread;
    private String id; 
    private String url;
    private Crawler crawler; 

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

    }

    @EJB
    private MessageManagerBean mmgr;
    
    @Override
    public void handleStart(Map<String, String[]> params) {
        crawlerThread = null;
        url = params.getOrDefault("site", new String[] {"www.google.com"})[0];
        if (! url.startsWith("https://")) {
            url = "https://" + url;
        }
        System.out.println("Starting fetcher for site: " + url);
        crawler = new Crawler(url, "stan");
        crawler.addListener(this);
        crawlerThread = new Thread(crawler);
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

    @Override
    public void onPage(String text) {
        List<AID> receivers = AgentManager.getInstance().getAllOnlineAgents()
            .stream()
            .map(x -> x.getId())
            .filter(x -> x.getType().getFullName().equals("stefan.agents.acasp.processor"))
            .collect(Collectors.toList());
        messageManager.post(new ACLMessage(
                        Performative.ACCEPT, 
                        this.getId(), 
                        receivers, 
                        null, 
                        text,
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
}