package br.com.fiap.paymentservice.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentRequest (
        @NotNull(message = "Order ID cannot be null")
        UUID orderId,

        @NotNull(message = "Total amount cannot be null")
        @Positive(message = "Total amount must be positive")
        BigDecimal totalAmount
) {
}
