package com.takehome;

import com.codahale.metrics.health.HealthCheck;
import com.takehome.api.Decrypt;
import com.takehome.api.PushAndRecalculate;
import com.takehome.api.PushAndRecalculateAndEncrypt;
import com.takehome.api.Reset;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class DropWizardProjectApplication extends Application<DropWizardProjectConfiguration> {
//    private static DoubleSummaryStatistics runningStat;
    private static DescriptiveStatistics runningStat;

    public static void main(final String[] args) throws Exception {
        new DropWizardProjectApplication().run(args);
    }
    public DropWizardProjectApplication(){
        runningStat = new DescriptiveStatistics();
        runningStat.clear();
    }

    @Override
    public String getName() {
        return "DropWizardProject";
    }

    @Override
    public void initialize(final Bootstrap<DropWizardProjectConfiguration> bootstrap) {
    }

    public static String pushAndRecalculate(int num){
        runningStat.addValue(num);
        return runningStat.getMean() + " " + Math.sqrt(runningStat.getPopulationVariance());
    }

    public static byte[] pushRecalculateAndEncrypt(int num){
        return null;
    }

    public static String decrypt(byte [] encryptedNumber){
        return null;
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
