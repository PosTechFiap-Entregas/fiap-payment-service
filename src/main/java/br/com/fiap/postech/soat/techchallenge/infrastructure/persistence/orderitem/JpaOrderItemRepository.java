package br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.orderitem;

import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaOrderItemRepository extends JpaRepository<OrderItemEntity, UUID>, JpaSpecificationExecutor<OrderItemEntity> {
    Optional<OrderItemEntity> findOrderItemById(UUID id);
    List<OrderItemEntity> findItemsByOrder(OrderEntity order);
//    void updateOrderItem(UUID id, UUID orderId, UUID productId);
    void deleteByOrder(OrderEntity order);
}
