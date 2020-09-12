package agents.acasp;

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
import com.stefan.data.Performative;
import com.stefan.message.MessageManager;
import com.stefan.message.MessageManagerBean;
import javax.ejb.Local;
import java.lang.Thread;
import javax.ejb.DependsOn;


@LocalBean
public class Processor implements Agent {

    String[] words;

    @Override
    public AID getId() {
        return new AID("", "localhost", new AgentType("processor", "stefan.agents.acasp"));
    }

    @Override
    public void handleMessage(ACLMessage msg) {
        System.out.println("Getting message");
        boolean matched = true;
        for (String word : words) {
            if (! msg.getContent().toLowerCase().contains(word.toLowerCase())) {
                matched = false;
            }
        }
        if (matched) {
            System.out.println("Found match in processor");
            List<AID> receivers = AgentManager.getInstance().getAllOnlineAgents()
            .stream()
            .map(x -> x.getId())
            .filter(x -> x.getType().getFullName().equals("stefan.agents.acasp.master"))
            .collect(Collectors.toList());
            mmgr.post(new ACLMessage(
                        Performative.DEFAULT,
                        this.getId(), 
                        receivers, 
                        null, 
                        msg.getContent(),
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
                    )
                );
        }
        
    }

    private MessageManager mmgr;
    
    @Override
    public void handleStart(Map<String, String[]> params) {
        words = params.getOrDefault("words", new String[] {""})[0].split(",");
        System.out.println("Starting processor that matches sites with all these words:");
        for (String w : words) {
            System.out.println("* " + w);
        }
    }

    @Override
    public void handleStop() {

    }

    @Override
    public void init(MessageManager msg) {
        this.mmgr = msg;

    }

    @Override
    public void deinit() {

    }
    
}