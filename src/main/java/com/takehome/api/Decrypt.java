package com.takehome.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import static com.takehome.DropWizardProjectApplication.*;

@Path("decrypt")
public class Decrypt {
    @POST
    public String post(byte [] encryptedNumber) throws Exception {
        return decrypt(encryptedNumber, key, iv);
    }
}
