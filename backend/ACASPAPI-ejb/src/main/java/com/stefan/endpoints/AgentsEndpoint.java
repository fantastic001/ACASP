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
import javax.validation.constraints.Positive;
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
import com.stefan.message.MessageManager;

@Stateless
@Path("agents")
public class AgentsEndpoint {
	@Context
	private HttpServletRequest request;

	@EJB
	private Control control;

	@EJB
	private MessageManager messageManager;

	@GET
	@Path("classes")
	@Produces("application/json")
	public Collection<AgentType> getAllTypes() {
		return AgentManager.getInstance().getAgentTypes();
	}

	@GET
	@Path("running")
	@Produces("application/json")
	public Collection<RunningAgent> getAllRunningAgents() {
		return AgentManager.getInstance().getOnlineAgents();
	}


	@POST
	@Path("running")
	@Produces("application/json")
	public Collection<RunningAgent> setAllRunningAgents(Collection<RunningAgent> agents) {
		AgentManager.getInstance().setAllOnlineAgents(agents);
		return agents;
	}

	@PUT
	@Path("running/{type}/{name}")
	@Produces("application/json")
	public RunningAgent runAgent(@PathParam("type") String type, @PathParam("name") String name) {
		try {
			Agent agent = AgentManager.getInstance().agentLookup(type);
			agent.init(messageManager);
			RunningAgent ra =  AgentManager.getInstance().login(name, agent, request.getParameterMap());
			control.getControl().runAgent(ra);
			return ra;
		} catch (AgentRunErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	@DELETE
	@Path("running/{name}")
	@Produces("application/json")
	public String stopAgent(@PathParam("name") String name) {
		AgentManager.getInstance().logout(name);
		control.getControl().agentRemoved(name);
		return name;
	}

	
}
