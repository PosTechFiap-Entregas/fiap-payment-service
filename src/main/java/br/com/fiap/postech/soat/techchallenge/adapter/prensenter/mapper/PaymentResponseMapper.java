package br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper;

import br.com.fiap.postech.soat.techchallenge.application.dto.response.PaymentResponse;
import br.com.fiap.postech.soat.techchallenge.domain.models.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentResponseMapper {

    default PaymentResponse toResponse(Payment payment) {
        if (payment == null) return null;

        return new PaymentResponse(
                payment.getId(),
                payment.getQrCode(),
                payment.getStatus()
        );
    }
}