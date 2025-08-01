package br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.payment;

import br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper.PaymentMapper;
import br.com.fiap.postech.soat.techchallenge.domain.models.Payment;
import br.com.fiap.postech.soat.techchallenge.adapter.gateway.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class PaymentRepository implements PaymentGateway {

    private final JpaPaymentRepository repository;
    private final PaymentMapper mapper;
    private final RestTemplate restTemplate;

    @Value("${mercado-pago.access-token}")
    private String ACCESS_TOKEN ;
    @Value("${mercado-pago.collector-id}")
    private String COLLECTOR_ID ;
    @Value("${mercado-pago.pos-id}")
    private String POS_ID;
    @Value("${mercado-pago.notification-url}")
    private String NOTIFICATION_URL;

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

}
