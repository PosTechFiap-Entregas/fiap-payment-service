package br.com.fiap.postech.soat.techchallenge.application.usercases;

import java.util.UUID;

public interface PaymentOrderUseCase {
    void updateOrderByPayment(UUID orderId);
}
