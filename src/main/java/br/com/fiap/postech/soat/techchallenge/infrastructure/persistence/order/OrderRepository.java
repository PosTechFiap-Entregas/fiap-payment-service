package br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.order;

import br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper.OrderItemMapper;
import br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper.OrderMapper;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.orderitem.OrderItemEntity;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.orderitem.JpaOrderItemRepository;
import br.com.fiap.postech.soat.techchallenge.domain.models.*;
import br.com.fiap.postech.soat.techchallenge.adapter.gateway.OrderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepository implements OrderGateway {

    private final JpaOrderRepository orderRepository;
    private final JpaOrderItemRepository itemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public void save(Order order) {
        OrderEntity entity = orderMapper.toEntity(order);
        orderRepository.save(entity);
    }

    @Override
    public List<Order> findAll(){
        List<OrderEntity> all = orderRepository.findAll();
        return orderMapper.toDomainList(all);
    }
    @Override
    public Optional<Order> findOrderById(UUID id) {
        return orderRepository.findById(id).map(orderMapper::toDomain);
    }

    @Override
    public List<Order> findOrdersByCustomer(Customer customer) {
        List<OrderEntity> ordersByCpfCustomer = orderRepository.findAllByOptionalFilters(null, customer.getId());
        return ordersByCpfCustomer.stream().map(orderMapper::toDomain).toList();
    }

    @Override
    public List<Order> findOrdersByFilters(OrderStatus status, UUID customerId) {
        List<OrderEntity> orderEntities = orderRepository.findAllByOptionalFilters(status, customerId);
        return orderEntities.stream().map(orderMapper::toDomain).toList();
    }

    @Override
    public List<Order> findOrdersByStatus(OrderStatus status) {
        List<OrderEntity> ordersByStatus = orderRepository.findAllByOptionalFilters(status, null);
        return ordersByStatus.stream().map(orderMapper::toDomain).toList();
    }

    @Override
    public void updateOrder(UUID orderId, OrderStatus orderStatus, List<OrderItem> items, Customer customer) {
        OrderEntity existingOrderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        Order order = orderMapper.toDomain(existingOrderEntity);
        order.update(customer, orderStatus);

        OrderEntity updatedOrderEntity = orderMapper.toEntity(order);

        orderRepository.save(updatedOrderEntity);
        itemRepository.deleteByOrder(existingOrderEntity);

        List<OrderItemEntity> newItemEntities = items.stream()
                .map(item -> orderItemMapper.toEntity(item, updatedOrderEntity))
                .toList();

        itemRepository.saveAll(newItemEntities);
    }

    @Override
    public void deleteById(UUID id) {
        orderRepository.deleteById(id);
    }
}
