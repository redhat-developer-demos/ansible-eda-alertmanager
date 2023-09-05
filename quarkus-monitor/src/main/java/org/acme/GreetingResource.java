package org.acme;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    private final MeterRegistry registry;
    private AtomicInteger currentMemory;
    private String hostname;

    GreetingResource(MeterRegistry registry) {
        this.registry = registry;
        currentMemory = this.registry.gauge("current.memory", Tags.empty(), new AtomicInteger(0));
        
        String _hostname = System.getenv("HOSTNAME");
        this.hostname = _hostname != null ? _hostname : "localhost";
    }

    @GET
    @Path("/consume/{amount}")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer consume(@PathParam("amount") int mem) {
        this.currentMemory.addAndGet(mem);
        return this.currentMemory.get();
    }

    @GET
    @Path("/consume/")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, Object> report() {
        HashMap<String, Object> m = new HashMap<>();

        m.put("memory", this.currentMemory.get());
        m.put("hostname", this.hostname);

        return m;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive running on " + this.hostname;
    }
}
