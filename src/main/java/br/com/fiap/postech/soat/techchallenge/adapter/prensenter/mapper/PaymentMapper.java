package br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper;

import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.order.OrderEntity;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.payment.PaymentEntity;
import br.com.fiap.postech.soat.techchallenge.domain.models.Order;
import br.com.fiap.postech.soat.techchallenge.domain.models.Payment;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public abstract class PaymentMapper {

    @Autowired
    private OrderMapper orderMapper;

    public Payment toDomain(PaymentEntity entity){
        if (entity == null) return null;
        Order order = orderMapper.toDomain(entity.getOrder());
        return new Payment(entity.getId(), entity.getStatus(), order, entity.getQrCode());
    }

    public PaymentEntity toEntity(Payment domain){
        if (domain == null) return null;
        OrderEntity order = orderMapper.toEntity(domain.getOrder());
        return new PaymentEntity(domain.getId(), domain.getStatus(),
                order, domain.getQrCode(), domain.getAmount());
    }
}
