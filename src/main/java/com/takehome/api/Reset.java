package com.takehome.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("push")
public class Reset {
    @POST
    public String reset(){
        return "The running totals for average and standard deviation have been rest to 0";
    }
}
