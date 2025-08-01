package br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper;

import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.customer.CustomerEntity;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.order.OrderEntity;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.orderitem.OrderItemEntity;
import br.com.fiap.postech.soat.techchallenge.domain.models.Customer;
import br.com.fiap.postech.soat.techchallenge.domain.models.Order;
import br.com.fiap.postech.soat.techchallenge.domain.models.OrderItem;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, ProductMapper.class})
public abstract class OrderMapper {

    @Autowired
    protected CustomerMapper customerMapper;

    @Autowired
    protected OrderItemMapper orderItemMapper;

    public Order toDomain(OrderEntity entity) {
        if (entity == null) return null;

        List<OrderItem> items = entity.getItems() != null
                ? entity.getItems().stream()
                .map(orderItemMapper::toDomain)
                .collect(Collectors.toList())
                : List.of();

        CustomerEntity customer = entity.getCustomer();
        Optional<Customer> customerOptional = customerMapper.toDomain(Optional.of(customer));

        return new Order(
                entity.getId(),
                customerOptional.get(),
                entity.getStatus(),
                entity.getObservation(),
                entity.getNumber(),
                items
        );
    }

    public OrderEntity toEntity(Order order) {
        if (order == null) return null;

        OrderEntity orderEntity = new OrderEntity(
                order.getId(),
                customerMapper.toEntity(order.getCustomer()),
                order.getStatus(),
                order.getObservation(),
                order.getNumber(),
                null
        );

        List<OrderItemEntity> items = order.getItems() != null
                ? order.getItems().stream()
                .map(item -> orderItemMapper.toEntity(item, orderEntity))
                .toList()
                : List.of();

        orderEntity.setItems(items);

        return orderEntity;
    }

    public List<Order> toDomainList(List<OrderEntity> orderEntities) {
        if (orderEntities.isEmpty()) {
            return List.of();
        }
        return orderEntities.stream()
                .map(this::toDomain).toList();
    }
}
