package br.com.fiap.paymentservice.adapter.prensenter.mapper;

import br.com.fiap.paymentservice.application.dto.response.PaymentResponse;
import br.com.fiap.paymentservice.domain.models.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentResponseMapper {

    default PaymentResponse toResponse(Payment payment) {
        if (payment == null) return null;

        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getQrCode(),
                payment.getStatus()
        );
    }
}