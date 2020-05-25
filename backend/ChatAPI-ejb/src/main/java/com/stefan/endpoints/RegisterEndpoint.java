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
import javax.servlet.http.*;
import javax.ws.rs.core.*;

import java.util.Date;

import javax.ejb.EJB;

import javax.ejb.Stateless;
import com.stefan.cluster.Control;
import com.stefan.cluster.Node;

@Stateless
@Path("register")
public class RegisterEndpoint {
	//@Context private HttpServletRequest request;

	@EJB
	Control control;

	@POST
	@Path("")
	@Produces("application/json")
	public String register(Node node) {
		control.getControl().nodeAdded(node);
		return "OK";
	}
}
