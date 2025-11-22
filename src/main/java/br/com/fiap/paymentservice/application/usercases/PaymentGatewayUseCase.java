package br.com.fiap.paymentservice.application.usercases;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentGatewayUseCase {
    String qrDynamic(BigDecimal amount, UUID id);
}
