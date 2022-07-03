package com.takehome.api;

import com.takehome.CryptoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PushAndRecalculateAndEncryptTest {

    @BeforeEach
    void setUp() throws Exception {
        CryptoService app = new CryptoService();
    }

    @AfterEach
    void tearDown() {
        CryptoService.clearAllStatistics();
    }

    @Test
    public void testMean() throws Exception {
        PushAndRecalculateAndEncrypt.post(4);
        PushAndRecalculateAndEncrypt.post(7);
        String res = PushAndRecalculateAndEncrypt.post(6);
        String actualEncryptedMean = res.split(",")[0];
        assertTrue(actualEncryptedMean.length()>0);
    }

    @Test
    public void testStandardDeviation() throws Exception {
        PushAndRecalculateAndEncrypt.post(4);
        PushAndRecalculateAndEncrypt.post(7);
        String res = PushAndRecalculateAndEncrypt.post(6);
        String actualEncryptedStandardDeviation = res.split(",")[1];
        assertTrue(actualEncryptedStandardDeviation.length()>0);
    }

    /** Tests the combiation of doing a /push request and a /encrypt request
     * @throws Exception
     */
    @Test
    public void testPushAndEncrypt() throws Exception {
        double expectedStandardDeviation = 1.247219128924647;
        PushAndRecalculateAndEncrypt.post(4);
        PushAndRecalculateAndEncrypt.post(7);
        String res = PushAndRecalculate.post(6);
        String actualStandardDeviation = res.split(",")[1];
        assertEquals(expectedStandardDeviation, Double.valueOf(actualStandardDeviation));
    }
}