package br.com.fiap.postech.soat.techchallenge.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderIdRequest (
        @NotNull(message = "{order.id.notnull}")
        UUID id
){
}
