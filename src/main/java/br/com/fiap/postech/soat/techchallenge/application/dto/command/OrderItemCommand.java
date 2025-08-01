package br.com.fiap.postech.soat.techchallenge.application.dto.command;

import java.util.UUID;

public record OrderItemCommand (UUID productId, int quantity) {
}
