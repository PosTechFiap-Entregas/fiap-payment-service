package br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.order;

import br.com.fiap.postech.soat.techchallenge.domain.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID> {

    @Query("SELECT o FROM OrderEntity o WHERE (:status IS NULL OR o.status = :status) AND (:customerId IS NULL OR o.customer.id = :customerId)")
    List<OrderEntity> findAllByOptionalFilters(
            @Param("status") OrderStatus status,
            @Param("customerId") UUID customerId
    );

}
