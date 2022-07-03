package com.takehome.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import static com.takehome.DropWizardProjectApplication.clearAllStatistics;

@Path("reset")
public class Reset {
    @POST
    public String reset(){
        clearAllStatistics();
        return "The runningStats have been reset for our \"Very Fun\" Crypto Service application!";
    }
}
