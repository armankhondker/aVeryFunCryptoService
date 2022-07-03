package com.takehome.api;

import com.takehome.CryptoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PushAndRecalculateTest {

    @BeforeEach
    void setUp() throws Exception {
        CryptoService app = new CryptoService();
    }

    @AfterEach
    void tearDown() {
        CryptoService.clearAllStatistics();
    }

    @Test
    public void testMean() {
        double expectedMean = (double) 17/3;
        PushAndRecalculate.post(4);
        PushAndRecalculate.post(7);
        String res = PushAndRecalculate.post(6);
        String actualMean = res.split(",")[0];
        assertEquals(expectedMean, Double.valueOf(actualMean));
    }

    @Test
    public void testStandardDeviation() {
        double expectedStandardDeviation = 1.2472191289246;
        PushAndRecalculate.post(4);
        PushAndRecalculate.post(7);
        String res = PushAndRecalculate.post(6);
        String actualStandardDeviation = res.split(",")[1];
        assertEquals(expectedStandardDeviation, Double.valueOf(expectedStandardDeviation));
    }
}