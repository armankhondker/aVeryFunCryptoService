package com.takehome.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("decrypt")
public class Decrypt {
    @POST
    public Double get(byte [] encryptedNumber){
        return 3.14;
    }
}
