package br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.orderitem;

import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.order.OrderEntity;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.product.ProductEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity {

    @Id
    private UUID id;

    @NotNull
    @Positive
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

}
