package com.takehome;

import com.codahale.metrics.health.HealthCheck;
import com.takehome.api.Decrypt;
import com.takehome.api.PushAndRecalculate;
import com.takehome.api.PushAndRecalculateAndEncrypt;
import com.takehome.api.Reset;
import com.takehome.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropWizardProjectApplication extends Application<DropWizardProjectConfiguration> {
    public static int sum = 0;
    public static int length = 0;
    public static void main(final String[] args) throws Exception {
        new DropWizardProjectApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropWizardProject";
    }

    @Override
    public void initialize(final Bootstrap<DropWizardProjectConfiguration> bootstrap) {

    }

    public static String pushAndRecalculate(int num){
        sum+=num;
        length++;
        return "Current val: " + num + "Total sum:" + sum;
    }


    public int getMean(){
        return 1;
    }

    public int getStandardDeviation(){
        return -1;
    }

    @Override
    public void run(final DropWizardProjectConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new PushAndRecalculate());
        environment.jersey().register(new PushAndRecalculateAndEncrypt());
        environment.jersey().register(new Decrypt());
        environment.jersey().register(new Reset());
        environment.healthChecks().register("CryptoService", new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                return Result.healthy();
            }
        });
    }
}
