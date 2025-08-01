package br.com.fiap.postech.soat.techchallenge.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProducIdRequest(
        @NotNull(message = "{product.id.notnull}")
        UUID id
) {
}
