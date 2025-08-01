package br.com.fiap.postech.soat.techchallenge.application.usercases;

import br.com.fiap.postech.soat.techchallenge.domain.models.Order;
import br.com.fiap.postech.soat.techchallenge.domain.models.Payment;
import br.com.fiap.postech.soat.techchallenge.domain.models.PaymentStatus;

import java.util.UUID;

public interface ManagePaymentUseCase {
    void create(Order order);
    void update(PaymentStatus status, UUID id);
    void pay(PaymentStatus status, UUID id);
    Payment getById(UUID id);
    Payment getByOrderId(UUID orderId);
}
