package br.com.fiap.paymentservice.domain.models;

import br.com.fiap.paymentservice.domain.exceptions.InvalidStatusException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentStatusTest {

    @Test
    void next_fromPending_returnsPaid() {
        PaymentStatus next = PaymentStatus.PENDING.next();
        assertEquals(PaymentStatus.PAID, next);
    }

    @Test
    void next_fromPaid_throwsInvalidStatusException() {
        assertThrows(InvalidStatusException.class, () -> PaymentStatus.PAID.next());
    }

    @Test
    void verifyTransition_trueAndFalse() {
        assertTrue(PaymentStatus.PENDING.verifyTransition(PaymentStatus.PAID));
        assertThrows(InvalidStatusException.class, () -> PaymentStatus.PAID.verifyTransition(PaymentStatus.PENDING));
    }
}

