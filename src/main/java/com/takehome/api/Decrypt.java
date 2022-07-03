package com.takehome.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import static com.takehome.DropWizardProjectApplication.decrypt;

@Path("decrypt")
public class Decrypt {
    @POST
    public String post(byte [] encryptedNumber){
        return decrypt(encryptedNumber);
    }
}
