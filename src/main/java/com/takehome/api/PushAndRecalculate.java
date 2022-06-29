package com.takehome.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("push/{number}")
public class PushAndRecalculate {
    @GET
    public String get(@PathParam("number") int num){
        return "HEY: " + num;
    }
}
