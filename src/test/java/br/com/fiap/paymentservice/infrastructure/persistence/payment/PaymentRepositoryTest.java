package br.com.fiap.paymentservice.infrastructure.persistence.payment;

import br.com.fiap.paymentservice.adapter.prensenter.mapper.PaymentMapper;
import br.com.fiap.paymentservice.application.dto.pagination.PagedResult;
import br.com.fiap.paymentservice.domain.models.Payment;
import br.com.fiap.paymentservice.domain.models.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentRepositoryTest {

    private JpaPaymentRepository jpa;
    private PaymentMapper mapper;
    private PaymentRepository repository;

    @BeforeEach
    void setup() {
        jpa = mock(JpaPaymentRepository.class);
        mapper = new PaymentMapper() {};
        repository = new PaymentRepository(jpa, mapper);
    }

    @Test
    void save_callsJpaSave() {
        Payment p = new Payment(UUID.randomUUID(), PaymentStatus.PENDING, UUID.randomUUID(), BigDecimal.ONE, null);
        repository.save(p);
        verify(jpa, times(1)).save(any());
    }

    @Test
    void findById_mapsEntityToDomain() {
        UUID id = UUID.randomUUID();
        PaymentEntity entity = new PaymentEntity(id, PaymentStatus.PENDING, UUID.randomUUID(), null, BigDecimal.ONE);
        when(jpa.findById(id)).thenReturn(Optional.of(entity));

        Optional<Payment> res = repository.findById(id);
        assertTrue(res.isPresent());
        assertEquals(id, res.get().getId());
    }

    @Test
    void findAll_returnsPagedResult() {
        PaymentEntity e1 = new PaymentEntity(UUID.randomUUID(), PaymentStatus.PENDING, UUID.randomUUID(), null, BigDecimal.ONE);
        Page<PaymentEntity> page = new PageImpl<>(List.of(e1), PageRequest.of(0, 10), 1);
        when(jpa.findAll(PageRequest.of(0,10))).thenReturn(page);

        PagedResult<Payment> result = repository.findAll(0,10);
        assertEquals(1, result.getTotalElements());
        assertEquals(0, result.getPage());
        assertEquals(1, result.getContent().size());
    }

    @Test
    void findByOrderId_returnsPagedResult() {
        UUID orderId = UUID.randomUUID();
        PaymentEntity e1 = new PaymentEntity(UUID.randomUUID(), PaymentStatus.PENDING, orderId, null, BigDecimal.ONE);
        Page<PaymentEntity> page = new PageImpl<>(List.of(e1), PageRequest.of(1, 5), 1);
        when(jpa.findByOrderId(orderId, PageRequest.of(1,5))).thenReturn(page);

        PagedResult<Payment> result = repository.findByOrderId(orderId,1,5);
        assertEquals(6, result.getTotalElements());
        assertEquals(1, result.getPage());
        assertEquals(1, result.getContent().size());
    }
}
