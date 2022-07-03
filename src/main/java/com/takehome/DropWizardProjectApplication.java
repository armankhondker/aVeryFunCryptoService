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
    public static SecretKey key;
    public static byte[] iv;

    public static void main(final String[] args) throws Exception {
        new DropWizardProjectApplication().run(args);
    }
    public DropWizardProjectApplication() throws Exception {
        runningStat = new DescriptiveStatistics();
        key = createKey();
        iv = generateIv().getIV();
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
        double mean = runningStat.getMean();
        double standardDeviation = Math.sqrt(runningStat.getPopulationVariance());
        return mean + ", " + standardDeviation;
    }

    public static String pushRecalculateAndEncrypt(int num) throws Exception{
        runningStat.addValue(num);
        double mean = runningStat.getMean();
        double standardDeviation = Math.sqrt(runningStat.getPopulationVariance());
        byte [] encryptedMean = encrypt(String.valueOf(mean), key, iv);
        byte [] encryptedStandardDeviation = encrypt(String.valueOf(standardDeviation), key, iv);
        System.out.println(decrypt(base64Encode(encryptedMean), key, iv));
        System.out.println(decrypt(base64Encode(encryptedStandardDeviation), key, iv));
        return base64Encode(encryptedMean) + ", " + base64Encode(encryptedStandardDeviation);
    }

    public static byte[] encrypt (String plainText, SecretKey key, byte [] iv) throws Exception{
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        return cipher.doFinal(plainText.getBytes());
    }

    public static String decrypt(String cipherText, SecretKey key, byte [] iv) throws Exception{
        byte [] cipherTextArray = base64Decode(cipherText);
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] result = cipher.doFinal(cipherTextArray);
        return new String(result);
    }

    public static SecretKey createKey() throws Exception {
        SecureRandom securerandom = new SecureRandom();
        KeyGenerator keygenerator = KeyGenerator.getInstance(AES);
        keygenerator.init(256, securerandom);
        return keygenerator.generateKey();
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String base64Encode(byte[] encryptedNumber){
        return Base64.getEncoder().encodeToString(encryptedNumber);
    }
    public static byte [] base64Decode(String encryptedNumber){
        return Base64.getDecoder().decode(encryptedNumber);
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
