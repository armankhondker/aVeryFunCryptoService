package com.takehome.api;


import com.takehome.DropWizardProjectApplication;

import javax.ws.rs.*;

@Path("push")
public class PushAndRecalculate {
    @POST
    @Consumes("application/json")
    public String post(int number){
        DropWizardProjectApplication.sum += number;
        return "Current val: " + number + "Total sum:" + DropWizardProjectApplication.sum;
    }
}
