package br.com.fiap.postech.soat.techchallenge.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(

        UUID customerId,

        @Valid
        @NotEmpty(message = "{orders.items.notempty}")
        @NotNull(message = "{orders.items.notnull}")
        List<OrderItemRequest> items,

        String observation
) {
}
