package com.takehome.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import static com.takehome.CryptoService.pushRecalculateAndEncrypt;

@Path("encrypt")
public class PushAndRecalculateAndEncrypt {
    @POST
    @Consumes("application/json")
    public String post(int num) throws Exception {
        return pushRecalculateAndEncrypt(num);
    }
}
