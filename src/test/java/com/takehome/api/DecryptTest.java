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
    public void decryptTest() {

    }

}