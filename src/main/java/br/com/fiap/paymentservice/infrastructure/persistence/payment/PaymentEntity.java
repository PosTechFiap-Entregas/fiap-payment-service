package br.com.fiap.paymentservice.infrastructure.persistence.payment;

import br.com.fiap.paymentservice.domain.models.PaymentStatus;
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

    @Column(name = "order_id", nullable = false, columnDefinition = "uuid")
    private UUID orderId;

    private String qrCode;

    private BigDecimal amount;
}
