package org.acme;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/listen")
public class GreetingResource {

    @POST
    public void listen(String body) {
        System.out.println(body);
    }
}
