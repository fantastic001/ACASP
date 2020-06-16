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
import com.stefan.agent.AgentRunErrorException;
import com.stefan.cluster.Control;
import com.stefan.cluster.Node;
import com.stefan.data.Agent;
import com.stefan.data.AgentType;
import com.stefan.data.RunningAgent;

@Stateless
@Path("agents")
public class AgentsEndpoint {
	@Context
	private HttpServletRequest request;

	@EJB
	private Control control;

	@GET
	@Path("classes")
	@Produces("application/json")
	public Collection<AgentType> getAllTypes() {
		return AgentManager.getInstance().getAgents().stream().map(agent -> agent.getId().getType())
				.collect(Collectors.toList());
	}

	@GET
	@Path("running")
	@Produces("application/json")
	public Collection<RunningAgent> getAllRunningAgents() {
		return AgentManager.getInstance().getOnlineAgents();
	}

	@PUT
	@Path("running/{type}/{name}")
	@Produces("application/json")
	public Agent runAgent(@PathParam("type") String type, @PathParam("name") String name) {
		Optional<Agent> agent = AgentManager.getInstance().getAgents().stream().filter(
				agent_ -> agent_.getId().getType().getName().equals(name) && agent_.getId().getType().getModule().equals(type))
				.findFirst();
		if (agent.isPresent()) {
			System.out.println("Running agent " + name);
			try {
				AgentManager.getInstance().login(agent.get());
			} catch (AgentRunErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Agent not found");
		}
		return agent.orElse(null);
	}




	
	@DELETE
	@Path("running/{name}")
	@Produces("application/json")
	public RunningAgent stopAgent(@PathParam("name") String name) {
		Optional<RunningAgent> agent = AgentManager.getInstance().getOnlineAgents().stream()
			.filter(a -> a.getName().equals(name)).findFirst();
		if (agent.isPresent()) {
			AgentManager.getInstance().logout(agent.get().getAgent());
		}
		else {
			System.out.println("Specified agent not running with given name: " + name);
		}
		return agent.orElse(null);
	}

	
}
