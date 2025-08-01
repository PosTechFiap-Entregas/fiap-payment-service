package br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.order;

import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.customer.CustomerEntity;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.orderitem.OrderItemEntity;
import br.com.fiap.postech.soat.techchallenge.domain.models.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String observation;

    @Min(0)
    private Integer number;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items;
}
