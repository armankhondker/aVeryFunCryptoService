package com.takehome.api;


import com.takehome.DropWizardProjectApplication;

import javax.ws.rs.*;

import static com.takehome.DropWizardProjectApplication.pushAndRecalculate;

@Path("push")
public class PushAndRecalculate {
    @POST
    @Consumes("application/json")
    public String post(int number){
        return pushAndRecalculate(number);
//        DropWizardProjectApplication.sum += number;
//        return "Current val: " + number + "Total sum:" + DropWizardProjectApplication.sum;
    }
}
