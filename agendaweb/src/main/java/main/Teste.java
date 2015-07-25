package main;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/olaMundo")
public class Teste {
	
	@GET
	@Produces("text/plain")
	public String dizOla() {
	return "Olá, mundo REST!";
	}

}
