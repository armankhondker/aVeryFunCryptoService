package com.takehome;

import com.codahale.metrics.health.HealthCheck;
import com.takehome.api.PushAndRecalculate;
import com.takehome.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropWizardProjectApplication extends Application<DropWizardProjectConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DropWizardProjectApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropWizardProject";
    }

    @Override
    public void initialize(final Bootstrap<DropWizardProjectConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final DropWizardProjectConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        environment.jersey().register(new HelloWorldResource());
        environment.jersey().register(new PushAndRecalculate());
        environment.healthChecks().register("CryptoService", new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                return Result.healthy();
            }
        });
    }

}
