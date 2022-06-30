package com.takehome;

import com.codahale.metrics.health.HealthCheck;
import com.takehome.api.Decrypt;
import com.takehome.api.PushAndRecalculate;
import com.takehome.api.PushAndRecalculateAndEncrypt;
import com.takehome.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropWizardProjectApplication extends Application<DropWizardProjectConfiguration> {
    public static int sum = 0;
    public static void main(final String[] args) throws Exception {
        new DropWizardProjectApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropWizardProject";
    }

    @Override
    public void initialize(final Bootstrap<DropWizardProjectConfiguration> bootstrap) {
//        this.
    }

    @Override
    public void run(final DropWizardProjectConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new HelloWorldResource());
        environment.jersey().register(new PushAndRecalculate());
        environment.jersey().register(new PushAndRecalculateAndEncrypt());
        environment.jersey().register(new Decrypt());
        environment.healthChecks().register("CryptoService", new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                return Result.healthy();
            }
        });
    }
}
