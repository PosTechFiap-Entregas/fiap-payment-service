package br.com.fiap.postech.soat.techchallenge.adapter.gateway;

import br.com.fiap.postech.soat.techchallenge.domain.models.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderGateway {

    List<Order> findAll();
    void save(Order order);
    Optional<Order> findOrderById(UUID id);
    List<Order> findOrdersByCustomer(Customer customer);
    List<Order> findOrdersByFilters(OrderStatus status, UUID customerId);
    List<Order> findOrdersByStatus(OrderStatus status);
    void updateOrder(UUID orderId, OrderStatus orderStatus, List<OrderItem> orderItems, Customer customer);
    void deleteById(UUID id);
}
