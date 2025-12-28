package br.com.fiap.paymentservice.domain.models;

import br.com.fiap.paymentservice.domain.exceptions.InvalidStatusException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void setStatus_allowsValidTransition() {
        Payment p = new Payment(UUID.randomUUID(), PaymentStatus.PENDING, UUID.randomUUID(), BigDecimal.TEN, null);
        p.setStatus(PaymentStatus.PAID); // PENDING -> PAID is valid
        assertEquals(PaymentStatus.PAID, p.getStatus());
    }

    @Test
    void setStatus_throwsOnInvalidTransition() {
        Payment p = new Payment(UUID.randomUUID(), PaymentStatus.PAID, UUID.randomUUID(), BigDecimal.TEN, null);
        assertThrows(InvalidStatusException.class, () -> p.setStatus(PaymentStatus.PENDING));
    }

    @Test
    void updateQr_updatesQrCode() {
        Payment p = new Payment(UUID.randomUUID(), PaymentStatus.PENDING, UUID.randomUUID(), BigDecimal.TEN, null);
        p.updateQr("my-qr");
        assertEquals("my-qr", p.getQrCode());
    }
}

