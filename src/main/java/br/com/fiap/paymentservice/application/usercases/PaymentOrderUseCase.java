package br.com.fiap.paymentservice.application.usercases;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface PaymentOrderUseCase {
    void notifyOrderPayment(@NotNull UUID orderId,@NotNull UUID paymentId, @NotNull String status);
}

