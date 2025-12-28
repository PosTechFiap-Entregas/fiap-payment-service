package br.com.fiap.paymentservice.adapter.gateway;

import br.com.fiap.paymentservice.application.dto.pagination.PagedResult;
import br.com.fiap.paymentservice.domain.models.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentGateway {
    void save(Payment payment);
    void update(Payment payment);
    Optional<Payment> findById(UUID id);
    Optional<Payment> findByOrderId(UUID orderId);
    PagedResult<Payment> findAll(int page, int size);
    PagedResult<Payment> findByOrderId(UUID orderId, int page, int size);
}
