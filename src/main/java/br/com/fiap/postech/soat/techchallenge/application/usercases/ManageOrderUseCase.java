package br.com.fiap.postech.soat.techchallenge.application.usercases;

import br.com.fiap.postech.soat.techchallenge.domain.models.Order;
import br.com.fiap.postech.soat.techchallenge.domain.models.OrderStatus;
import br.com.fiap.postech.soat.techchallenge.application.dto.command.CreateOrderCommand;

import java.util.List;
import java.util.UUID;

public interface ManageOrderUseCase {
    Order getOrderById(UUID orderId);
    List<Order> getOrdersByFilters(OrderStatus orderStatus, UUID customerId);
    Order createOrder(CreateOrderCommand createOrderCommand);
    void updateOrderStatus(UUID orderId, OrderStatus orderStatus);
    void deleteOrder(UUID orderID);
    Order checkoutOrder(UUID orderID);
}
