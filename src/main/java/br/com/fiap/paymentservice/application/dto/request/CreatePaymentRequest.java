package br.com.fiap.paymentservice.application.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentRequest (
        UUID orderId,
        BigDecimal totalAmount
) {
}
