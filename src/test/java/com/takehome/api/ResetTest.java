package com.takehome.api;

import com.takehome.CryptoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResetTest {
    @BeforeEach
    void setUp() throws Exception {
        CryptoService app = new CryptoService();
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    public void resetTest() {
        String resetString = "The runningStats have been reset for our \"Very Fun\" Crypto Service application!";
        String res = Reset.reset();
        assertEquals(resetString, res);
    }
}