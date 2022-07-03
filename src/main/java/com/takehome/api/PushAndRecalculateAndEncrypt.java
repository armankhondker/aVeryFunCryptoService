package com.takehome.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import static com.takehome.DropWizardProjectApplication.pushRecalculateAndEncrypt;

@Path("encrypt")
public class PushAndRecalculateAndEncrypt {
    @POST
    @Consumes("application/json")
    public byte[] post(int num){
        return pushRecalculateAndEncrypt(num);
    }
}
