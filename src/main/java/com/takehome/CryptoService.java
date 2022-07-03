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

public class CryptoService extends Application<DropWizardProjectConfiguration> {
    private static DescriptiveStatistics runningStat;
    private static final String AES = "AES";
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";
    public static SecretKey key;
    public static byte[] iv;

    public static void main(final String[] args) throws Exception {
        new CryptoService().run(args);
    }
    public CryptoService() throws Exception {
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

    /**
     * @param num the number we want to add to our running statistics and encrypt
     * @return the encrypted calculated mean and standard deviation of all numbers accepted in a Base64 encoding
     * @throws Exception if our encryption algorithm fails
     */
    public static String pushRecalculateAndEncrypt(int num) throws Exception{
        String stats = pushAndRecalculate(num);
        String [] meanAndStandardDeviation = stats.split(",");
        byte [] encryptedMean = encrypt(meanAndStandardDeviation[0], key, iv);
        byte [] encryptedStandardDeviation = encrypt(meanAndStandardDeviation[1], key, iv);
        return base64Encode(encryptedMean) + ", " + base64Encode(encryptedStandardDeviation);
    }

    /**
     * @param plainText the text that we want to encrypt
     * @param key the key we will use for all symmetric encryption
     * @param iv the initialization vector we will use with an arbitrary value
     * @return the encrypted string represented as a byte array
     * @throws Exception if our encryption algorithm fails
     */
    public static byte[] encrypt (String plainText, SecretKey key, byte [] iv) throws Exception{
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        return cipher.doFinal(plainText.getBytes());
    }

    /**
     * @param cipherText the string representation of our cipherText array that we will decrypt
     * @param key the key we will use for all symmetric encryption
     * @param iv the initialization vector we will use with an arbitrary value
     * @return the plaintext decrypted version of our input
     * @throws Exception
     */
    public static String decrypt(String cipherText, SecretKey key, byte [] iv) throws Exception{
        byte [] cipherTextArray = base64Decode(cipherText);
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] result = cipher.doFinal(cipherTextArray);
        return new String(result);
    }

    /**
     * @return our secret key that we will use for symmetric encryption
     * @throws Exception if our secret key generations fails
     */
    public static SecretKey createKey() throws Exception {
        SecureRandom securerandom = new SecureRandom();
        KeyGenerator keygenerator = KeyGenerator.getInstance(AES);
        keygenerator.init(256, securerandom);
        return keygenerator.generateKey();
    }

    /**
     * @return an initialization vector that is required to avoid repetition during the encryption process
     */
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
