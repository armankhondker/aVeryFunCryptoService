package com.takehome.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import static com.takehome.CryptoService.clearAllStatistics;

@Path("reset")
public class Reset {
    @POST
    public static String reset(){
        clearAllStatistics();
        return "The runningStats have been reset for our \"Very Fun\" Crypto Service application!";
    }
}
