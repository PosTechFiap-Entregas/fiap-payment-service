package br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper;

import br.com.fiap.postech.soat.techchallenge.application.dto.request.CreateOrderRequest;
import br.com.fiap.postech.soat.techchallenge.application.dto.request.OrderItemRequest;
import br.com.fiap.postech.soat.techchallenge.application.dto.command.CreateOrderCommand;
import br.com.fiap.postech.soat.techchallenge.application.dto.command.OrderItemCommand;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper {

    default CreateOrderCommand toCommand(@NotNull CreateOrderRequest request){
        return new CreateOrderCommand(
                request.customerId(),
                toOrderItemCommand(request.items()),
                request.observation()
        );
    }

    default List<OrderItemCommand> toOrderItemCommand(@NotNull List<OrderItemRequest> itemRequests) {
        return itemRequests.stream()
                .map(item -> new OrderItemCommand(item.productId(), item.quantity()))
                .toList();
    }
}
