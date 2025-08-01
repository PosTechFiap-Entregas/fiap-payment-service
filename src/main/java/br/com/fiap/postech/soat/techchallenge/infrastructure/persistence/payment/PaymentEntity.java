package br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.payment;

import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.order.OrderEntity;
import br.com.fiap.postech.soat.techchallenge.domain.models.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderEntity order;
    private String qrCode;

    private BigDecimal amount;
}
