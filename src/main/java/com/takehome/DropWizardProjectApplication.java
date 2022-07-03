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
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DropWizardProjectApplication extends Application<DropWizardProjectConfiguration> {
    private static DescriptiveStatistics runningStat;
    private static final String AES = "AES";
    private static SecretKey key;

    public static void main(final String[] args) throws Exception {
        new DropWizardProjectApplication().run(args);
    }
    public DropWizardProjectApplication() throws Exception {
        runningStat = new DescriptiveStatistics();
        SecureRandom securerandom = new SecureRandom();
        KeyGenerator keygenerator = KeyGenerator.getInstance(AES);
        keygenerator.init(256, securerandom);
        key = keygenerator.generateKey();
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

    public static void clearAllStatistics(){
        runningStat.clear();
    }

    @Override
    public void run(final DropWizardProjectConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new PushAndRecalculate());
        environment.jersey().register(new PushAndRecalculateAndEncrypt());
        environment.jersey().register(new Decrypt());
        environment.jersey().register(new Reset());
        environment.healthChecks().register("\"Very Fun\" CryptoService!", new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                return Result.healthy();
            }
        });
    }
}
