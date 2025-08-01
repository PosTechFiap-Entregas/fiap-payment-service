package br.com.fiap.postech.soat.techchallenge.application.dto.request;
import br.com.fiap.postech.soat.techchallenge.domain.models.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderRequest (
        @NotNull(message = "Order status cannot be null.")
        OrderStatus status
){
}
