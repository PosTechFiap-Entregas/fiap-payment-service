package br.com.fiap.paymentservice.adapter.prensenter.handler;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationErrorResponseTest {

    @Test
    void record_fields() {
        ValidationErrorResponse r = new ValidationErrorResponse(400, List.of("a","b"));
        assertEquals(400, r.status());
        assertEquals(2, r.errors().size());
    }
}

