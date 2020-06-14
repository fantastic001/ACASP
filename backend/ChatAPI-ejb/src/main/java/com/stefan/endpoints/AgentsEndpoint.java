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
import java.util.Collection;

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
		return null;
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
		return null;
	}


	@DELETE
	@Path("running/{aid}")
	@Produces("application/json")
	public Agent stopAgent(@PathParam("aid") String aid) {
		return null;
	}

	
}
