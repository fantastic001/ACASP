package com.stefan.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import javax.annotation.Generated;
import javax.servlet.http.*;
import javax.ws.rs.core.*;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.jar.Attributes.Name;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Collections;

import javax.ejb.Stateless;
import javax.ejb.EJB;

import com.stefan.agent.AgentManager;
import com.stefan.cluster.Control;
import com.stefan.cluster.Node;
import com.stefan.data.Agent;
import com.stefan.data.AgentType;

@Stateless
@Path("agents")
public class AgentsEndpoint {
	@Context private HttpServletRequest request;

	@EJB 
	private Control control;

	@GET
	@Path("classes")
	@Produces("application/json")
	public Collection<AgentType> getAllTypes() {
		return AgentManager.getInstance().getAgents().stream().map(agent -> agent.getId().getType()).collect(Collectors.toList());
	}

	@GET
	@Path("running")
	@Produces("application/json")
	public Collection<Agent> getAllRunningAgents() {
		return AgentManager.getInstance().getOnlineAgents();
	}
	
	@PUT
	@Path("running/{type}/{name}")
	@Produces("application/json")
	public Agent runAgent(@PathParam("type") String type, @PathParam("name") String name) {
		Optional<Agent> agent = AgentManager.getInstance().getAgents().stream()
			.filter(agent_ -> agent_.getId().getName().equals(name) && agent_.getId().getType().toString().equals(type))
			.findFirst();
		if (agent.isPresent()) {
			System.out.println("Running agent " + name);
			agent.get().getId().setName(getRandomAgentName());
			AgentManager.getInstance().getOnlineAgents().add(agent.get());
			agent.get().handleStart();
		}
		else {
			System.out.println("Agent not found");
		}
		return agent.orElse(null);
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
	
	@DELETE
	@Path("running/{name}")
	@Produces("application/json")
	public Agent stopAgent(@PathParam("name") String name) {
		Optional<Agent> agent = AgentManager.getInstance().getOnlineAgents().stream()
			.filter(a -> a.getId().getName().equals(name)).findFirst();
		if (agent.isPresent()) {
			agent.get().handleStop();
			AgentManager.getInstance().getOnlineAgents().remove(agent.get());
		}
		else {
			System.out.println("Specified agent not running with given name: " + name);
		}
		return agent.orElse(null);
	}

	
}
