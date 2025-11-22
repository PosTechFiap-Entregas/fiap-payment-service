package br.com.fiap.paymentservice.application.dto.response;

import br.com.fiap.paymentservice.domain.models.PaymentStatus;

import java.util.UUID;

public record PaymentResponse(
        UUID id,
        String qrCode,
        PaymentStatus status
) {
}