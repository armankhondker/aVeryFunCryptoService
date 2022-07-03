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
import javax.crypto.Cipher;
import javax.crypto.spec
        .IvParameterSpec;
import java.util.Base64;
import javax.xml.bind
        .DatatypeConverter;

public class DropWizardProjectApplication extends Application<DropWizardProjectConfiguration> {
    private static DescriptiveStatistics runningStat;
    private static final String AES = "AES";
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";
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
        return runningStat.getMean() + ", " + Math.sqrt(runningStat.getPopulationVariance());
    }

    public static String pushRecalculateAndEncrypt(int num) throws Exception{
        double mean = runningStat.getMean();
        double standardDeviation = Math.sqrt(runningStat.getPopulationVariance());
        return base64Encode(encrypt(mean+ "", key, generateIv().getIV())) + " SPLLITER " + base64Encode(encrypt(standardDeviation+"", key, generateIv().getIV()));
    }
    public static String base64Encode(byte[] encryptedNumber){
        return Base64.getEncoder().encodeToString(encryptedNumber);
    }

    public static byte[] encrypt (String plainText, SecretKey key, byte [] iv) throws Exception{
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        return cipher.doFinal(plainText.getBytes());
    }

    public static String decryptCaller(byte [] encryptedNumber) throws Exception {
        return decrypt(encryptedNumber, key, generateIv().getIV());
    }

    public static String decrypt(byte [] cipherText, SecretKey key, byte [] iv) throws Exception{
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] result = cipher.doFinal(cipherText);
        return new String(result);
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
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
