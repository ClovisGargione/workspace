package service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
@Path("helloworld")
public class HelloWorld {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld(){
        return "Olá, mundo rest!";
    }
}
