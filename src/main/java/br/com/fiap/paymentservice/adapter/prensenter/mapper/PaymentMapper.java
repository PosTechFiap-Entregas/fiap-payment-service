package br.com.fiap.paymentservice.adapter.prensenter.mapper;

import br.com.fiap.paymentservice.domain.models.Payment;
import br.com.fiap.paymentservice.infrastructure.persistence.payment.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PaymentMapper {

    public Payment toDomain(PaymentEntity entity){
        if (entity == null) return null;
        return new Payment(entity.getId(), entity.getStatus(), entity.getOrderId(), entity.getAmount(), entity.getQrCode());
    }

    public PaymentEntity toEntity(Payment domain){
        if (domain == null) return null;
        return new PaymentEntity(domain.getId(), domain.getStatus(),
                domain.getOrderId(), domain.getQrCode(), domain.getAmount());
    }
}
