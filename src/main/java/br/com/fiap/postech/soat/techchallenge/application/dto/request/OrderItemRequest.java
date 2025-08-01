package br.com.fiap.postech.soat.techchallenge.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record OrderItemRequest(
        @NotNull(message = "{orderItem.productId.notnull}")
        UUID productId,

        @NotNull(message = "{orderItem.quantity.notnull}")
        @Positive(message = "{orderItem.quantity.positive}")
        Integer quantity
) {
}
