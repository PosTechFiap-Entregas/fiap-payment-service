package br.com.fiap.paymentservice.infrastructure.persistence.payment;

import br.com.fiap.paymentservice.adapter.prensenter.mapper.PaymentMapper;
import br.com.fiap.paymentservice.application.dto.pagination.PagedResult;
import br.com.fiap.paymentservice.domain.models.Payment;
import br.com.fiap.paymentservice.adapter.gateway.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PaymentRepository implements PaymentGateway {

    private final JpaPaymentRepository repository;
    private final PaymentMapper mapper;

    @Override
    public void save(Payment payment) {
        repository.save(mapper.toEntity(payment));
    }

    @Override
    public void update(Payment payment) {
        repository.save(mapper.toEntity(payment));
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return repository.findByOrderId(orderId)
                .map(mapper::toDomain);
    }

    // PagedResult-based API (application layer)
    @Override
    public PagedResult<Payment> findAll(int page, int size) {
        Page<Payment> p = repository.findAll(PageRequest.of(page, size)).map(mapper::toDomain);
        return new PagedResult<>(p.getContent(), p.getTotalElements(), p.getNumber());
    }

    @Override
    public PagedResult<Payment> findByOrderId(UUID orderId, int page, int size) {
        Page<Payment> p = repository.findByOrderId(orderId, PageRequest.of(page, size)).map(mapper::toDomain);
        return new PagedResult<>(p.getContent(), p.getTotalElements(), p.getNumber());
    }

    // Spring Page-based API (compatibility)
    public Page<Payment> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDomain);
    }

    public Page<Payment> findByOrderId(UUID orderId, Pageable pageable) {
        return repository.findByOrderId(orderId, pageable).map(mapper::toDomain);
    }

}
