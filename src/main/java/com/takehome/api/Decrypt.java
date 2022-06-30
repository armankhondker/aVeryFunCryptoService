package com.takehome.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("decrypt")
public class Decrypt {
    @GET
    public Double get(byte [] encryptedNumber){
        return 3.14;
    }
}
