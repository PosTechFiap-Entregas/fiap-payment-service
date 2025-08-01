package br.com.fiap.postech.soat.techchallenge.application.dto.response;

import br.com.fiap.postech.soat.techchallenge.domain.models.PaymentStatus;

import java.util.UUID;

public record PaymentResponse(
        UUID id,
        String qrCode,
        PaymentStatus status
) {
}