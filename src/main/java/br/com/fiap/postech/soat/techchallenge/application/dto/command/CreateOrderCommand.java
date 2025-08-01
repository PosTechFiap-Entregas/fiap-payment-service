package br.com.fiap.postech.soat.techchallenge.application.dto.command;

import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(
        UUID customerId, List<OrderItemCommand> items, String observation
) {
}