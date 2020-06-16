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
import java.util.Collection;

import javax.servlet.http.*;
import javax.ws.rs.core.*;

import java.util.Date;

import javax.ejb.Stateless;

import javax.ejb.EJB;
import com.stefan.cluster.Control;
import com.stefan.cluster.Node;
import com.stefan.data.ACLMessage;
import com.stefan.message.MessageManager;
import com.stefan.message.MessageManagerBean;



@Stateless
@Path("node")
public class NodeEndpoint {
	//@Context private HttpServletRequest request;

	@EJB
	Control control;

	@POST
	@Path("")
	@Produces("application/json")
	public String newNode(Node node) {
		control.getControl().nodeAdded(node);
		return "OK";
	}
	

	@GET
	@Path("")
	@Produces("application/json")
	public String pingNode() {
		return "PONG";
	}

	@DELETE
	@Path("{alias}")
	@Produces("application/json")
	public String deleteNode(@PathParam("alias") String alias) {
		control.getControl().nodeRemoved(alias);
		return "OK";
	}

	@GET
	@Path("messages")
	@Produces("application/json")
	public Collection<ACLMessage> getMessages() {
		return null;
	}

	@EJB
	private MessageManagerBean msgmgr;

	@POST
	@Path("messages")
	@Produces("application/json")
	public ACLMessage sendMessage(ACLMessage message) {
		msgmgr.post(message);
		return message;
	}
}
