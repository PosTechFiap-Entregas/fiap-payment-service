package br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper;

import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.order.OrderEntity;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.orderitem.OrderItemEntity;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.product.ProductEntity;
import br.com.fiap.postech.soat.techchallenge.domain.models.OrderItem;
import br.com.fiap.postech.soat.techchallenge.domain.models.Product;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, OrderMapper.class})
public abstract class OrderItemMapper {

    @Autowired
    protected ProductMapper productMapper;

    public OrderItem toDomain(OrderItemEntity entity) {
        if (entity == null) return null;

        Product product = productMapper.toDomain(entity.getProduct());

        return new OrderItem(
                entity.getId(),
                entity.getQuantity(),
                product
        );
    }

    public OrderItemEntity toEntity(OrderItem orderItem, OrderEntity orderEntity) {
        if (orderItem == null) return null;

        ProductEntity productEntity = productMapper.toEntity(orderItem.getProduct());

        return new OrderItemEntity(
                orderItem.getId(),
                orderItem.getQuantity(),
                orderEntity,
                productEntity
        );
    }
}
