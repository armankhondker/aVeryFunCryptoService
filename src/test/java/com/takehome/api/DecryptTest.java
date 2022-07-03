package com.takehome.api;

import com.takehome.CryptoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecryptTest {

    @BeforeEach
    void setUp() throws Exception {
        CryptoService app = new CryptoService();
    }

    @AfterEach
    void tearDown() {
        CryptoService.clearAllStatistics();
    }

    @Test
    public void decryptMeanTest() throws Exception {
        PushAndRecalculateAndEncrypt.post(4);
        PushAndRecalculateAndEncrypt.post(7);
        String res = PushAndRecalculateAndEncrypt.post(6);
        String actualEncryptedMean = res.split(",")[0];
        String actualPlainText = Decrypt.post(actualEncryptedMean);
        assertEquals(String.valueOf((double)17/3), actualPlainText);
    }

    @Test
    public void decryptStandardDeviationTest() throws Exception {
        PushAndRecalculateAndEncrypt.post(4);
        PushAndRecalculateAndEncrypt.post(7);
        String res = PushAndRecalculateAndEncrypt.post(6);
        String actualEncryptedSD = res.split(",")[1].substring(1);
        String actualPlainText = Decrypt.post(actualEncryptedSD).substring(1);
        assertEquals(String.valueOf(1.247219128924647), actualPlainText);
    }

}