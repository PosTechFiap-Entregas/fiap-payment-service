package br.com.fiap.paymentservice.application.usercases;

import br.com.fiap.paymentservice.domain.models.Payment;
import br.com.fiap.paymentservice.domain.models.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public interface ManagePaymentUseCase {
    UUID create(UUID orderId, BigDecimal totalAmount);
    void pay(PaymentStatus status, UUID id);
    Payment getById(UUID id);
    Payment getByOrderId(UUID orderId);
}
