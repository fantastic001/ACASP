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
import com.stefan.data.Message;
import com.stefan.data.User;
import com.stefan.message.MessageManager; 
import com.stefan.user.UserManager;

import javax.ejb.Stateless;
import javax.ejb.EJB;
import com.stefan.cluster.Control;
import com.stefan.cluster.Node;

@Stateless
@Path("messages")
public class MessagesEndpoint {
	@Context private HttpServletRequest request;

	@EJB 
	private Control control;

	@POST
	@Path("all")
	@Produces("application/json")
	public Message all(Message message) {
		MessageManager.getInstance().broadcastMessage(message, UserManager.getInstance().getUsers());
		return message;
	}

	@POST
	@Path("user")
	@Produces("application/json")
	public Message user(Message message) {
		User toUser = null;
		User fromUser = null;
		String from = message.getFromUsername();
		String to = message.getToUsername();
        for (User user : UserManager.getInstance().getUsers()) {
            if (user.getUsername().equals(from)) fromUser = user;
            if (user.getUsername().equals(to)) toUser = user;
        }
		if (toUser == null || fromUser == null) return null;
		System.out.println("All users found in this or other nodes.");
        if (! control.getControl().hasUser(fromUser)) {
			System.out.println("Could not find sender here");
            return null;
        }
        if (! control.getControl().hasUser(toUser)) {
			System.out.println("Could not findreciever here");
            Node node = control.getControl().findNode(toUser.getHostAlias());
            node.postAsync("/node/messages/", message);
            return MessageManager.getInstance().createMessage(
				message.getFromUsername(), 
				message.getToUsername(), 
				message.getSubject(), 
				message.getText()
			);
        }
		return MessageManager.getInstance().createMessage(
				message.getFromUsername(), 
				message.getToUsername(), 
				message.getSubject(), 
				message.getText()
			);
	}
	@GET 
	@Path("")
	@Produces("application/json")
	public Collection<Message> getInbox() {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) return new ArrayList<>();
        System.out.println("Getting messages for " + user.getUsername());
        if (! control.getControl().hasUser(user)) {
            System.out.println("This node does not have this user");
            return new ArrayList<>();
        }
        System.out.println("User found");

		return MessageManager.getInstance().getMessages(user);
	}
}
