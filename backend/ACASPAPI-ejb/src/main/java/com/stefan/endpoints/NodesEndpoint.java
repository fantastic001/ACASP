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

import javax.ejb.Stateless;
import com.stefan.cluster.Control;
import javax.ejb.EJB;
import com.stefan.cluster.Node;
import java.util.Collection;

@Stateless
@Path("nodes")
public class NodesEndpoint {
	//@Context private HttpServletRequest request;

	@EJB
	private Control control;

	@POST
	@Path("")
	@Produces("application/json")
	public String submitAllNodes(Node node) {
		System.out.println("/nodes/ endpoint");
		control.getControl().nodeAdded(node);
		return "OK";
	}
}
