package com.takehome.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("encrypt")
public class PushAndRecalculateAndEncrypt {
    @POST
    @Consumes("application/json")
    public byte[] get(int num){
        System.out.println("TEST");
        return null;
    }
}
