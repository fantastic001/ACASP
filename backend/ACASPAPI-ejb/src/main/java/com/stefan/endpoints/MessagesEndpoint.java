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
import java.util.List;
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
import com.stefan.data.ACLMessage;
import com.stefan.data.Agent;
import com.stefan.data.AgentType;
import com.stefan.data.RunningAgent;
import com.stefan.message.MessageManager;

@Stateless
@Path("messages")
public class MessagesEndpoint {
	@Context
	private HttpServletRequest request;

	@EJB
    private Control control;
    
    @EJB
    MessageManager messageManager;

	@POST
	@Path("")
	@Produces("application/json")
	public ACLMessage sendMessage(ACLMessage message) {
		// System.out.println("Got message from other node");
		messageManager.post(message);
		return message;
    }
    
    @GET
	@Path("")
	@Produces("application/json")
	public List<String> getPerformatives() {
        return messageManager.getPerformatives();
	}

}
