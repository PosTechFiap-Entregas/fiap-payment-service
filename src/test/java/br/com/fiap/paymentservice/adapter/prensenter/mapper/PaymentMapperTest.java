package br.com.fiap.paymentservice.adapter.prensenter.mapper;

import br.com.fiap.paymentservice.domain.models.Payment;
import br.com.fiap.paymentservice.domain.models.PaymentStatus;
import br.com.fiap.paymentservice.infrastructure.persistence.payment.PaymentEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMapperTest {

    private final PaymentMapper mapper = new PaymentMapper() {};

    @Test
    void toDomain_null_returnsNull() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    void toEntity_and_toDomain_roundtrip() {
        UUID id = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        PaymentEntity entity = new PaymentEntity(id, PaymentStatus.PENDING, orderId, null, BigDecimal.valueOf(123.45));

        Payment domain = mapper.toDomain(entity);
        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(orderId, domain.getOrderId());
        assertEquals(BigDecimal.valueOf(123.45), domain.getAmount());

        PaymentEntity entity2 = mapper.toEntity(domain);
        assertNotNull(entity2);
        assertEquals(entity.getId(), entity2.getId());
        assertEquals(entity.getOrderId(), entity2.getOrderId());
        assertEquals(entity.getAmount(), entity2.getAmount());
    }
}

