package org.acme;

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


    GreetingResource(MeterRegistry registry) {
        this.registry = registry;
        currentMemory = this.registry.gauge("current.memory", Tags.empty(), new AtomicInteger(0));
    }

    @GET
    @Path("/consume/{amount}")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer consume(@PathParam("amount") int mem) {
        this.currentMemory.addAndGet(mem);
        return this.currentMemory.get();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }
}
