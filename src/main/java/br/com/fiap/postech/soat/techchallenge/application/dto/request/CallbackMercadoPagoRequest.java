package br.com.fiap.postech.soat.techchallenge.application.dto.request;

import br.com.fiap.postech.soat.techchallenge.domain.models.PaymentStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CallbackMercadoPagoRequest (
        @NotNull(message = "Payment status cannot be null.")
        PaymentStatus status,

        @NotNull(message = "ID cannot be null.")
        UUID id
) {

}
