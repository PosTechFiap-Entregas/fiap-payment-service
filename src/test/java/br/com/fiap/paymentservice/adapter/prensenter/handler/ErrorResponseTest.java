package br.com.fiap.paymentservice.adapter.prensenter.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void record_fields() {
        ErrorResponse r = new ErrorResponse(500, "oops");
        assertEquals(500, r.status());
        assertEquals("oops", r.error());
    }
}

