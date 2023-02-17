package com.megaease.easeagent.sofarpc.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/test")
public interface AService {

	@GET
	@Path("/sayHi/{name}")
	String sayHi(@PathParam("name") String name);
}
