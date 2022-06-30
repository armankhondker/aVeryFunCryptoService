package com.takehome.api;

import com.takehome.DropWizardProjectApplication;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("push")
public class Reset {
    @POST
    public String reset(){
        DropWizardProjectApplication.sum =0;
        return "The running totals for average and standard deviation have been rest to 0";
    }
}
