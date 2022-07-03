package com.takehome.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import static com.takehome.DropWizardProjectApplication.pushAndRecalculate;


@Path("push")
public class PushAndRecalculate {
    @POST
    @Consumes("application/json")
    public String post(int number){
            return pushAndRecalculate(number);
    }
}
