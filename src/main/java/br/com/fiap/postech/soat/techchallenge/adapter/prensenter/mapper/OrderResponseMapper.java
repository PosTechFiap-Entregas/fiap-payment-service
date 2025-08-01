package br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper;

import br.com.fiap.postech.soat.techchallenge.application.dto.response.OrderItemResponse;
import br.com.fiap.postech.soat.techchallenge.application.dto.response.OrderResponse;
import br.com.fiap.postech.soat.techchallenge.application.dto.response.ProductResponse;
import br.com.fiap.postech.soat.techchallenge.domain.models.Order;
import br.com.fiap.postech.soat.techchallenge.domain.models.OrderItem;
import br.com.fiap.postech.soat.techchallenge.domain.models.Product;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderResponseMapper {

    ProductResponseMapper productMapper = new ProductResponseMapper() {
        @Override
        public ProductResponse toResponse(Product product) {
            return ProductResponseMapper.super.toResponse(product);
        }
    };

    default OrderResponse toResponse(@NotNull Order order) {
        //TODO definir total de itens
        return new OrderResponse(order.getId(), order.getCustomer().getName(), order.getCustomer().getCpf(), order.getStatus(), order.getNumber(), toOrderItemResponse(order.getItems()));
    }

    default List<OrderItemResponse> toOrderItemResponse(List<OrderItem> ordersItems) {
        return ordersItems.stream()
                .map((item ->
                        new OrderItemResponse(productMapper.toResponse(item.getProduct()), item.getQuantity())))
                .toList();
    }
}
