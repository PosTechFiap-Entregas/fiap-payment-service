package br.com.fiap.paymentservice.adapter.prensenter.mapper;

import br.com.fiap.paymentservice.application.dto.response.PaymentResponse;
import br.com.fiap.paymentservice.domain.models.Payment;
import br.com.fiap.paymentservice.domain.models.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentResponseMapperTest {

    private final PaymentResponseMapper mapper = new PaymentResponseMapper() {};

    @Test
    void toResponse_null_returnsNull() {
        assertNull(mapper.toResponse(null));
    }

    @Test
    void toResponse_mapsFields() {
        UUID id = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Payment p = new Payment(id, PaymentStatus.PENDING, orderId, BigDecimal.valueOf(10), "qr");
        PaymentResponse r = mapper.toResponse(p);
        assertNotNull(r);
        assertEquals(id, r.id());
        assertEquals(orderId, r.orderId());
        assertEquals("qr", r.qrCode());
        assertEquals(PaymentStatus.PENDING, r.status());
    }
}

