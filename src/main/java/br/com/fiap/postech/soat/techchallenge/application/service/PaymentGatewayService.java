package br.com.fiap.postech.soat.techchallenge.application.service;

import br.com.fiap.postech.soat.techchallenge.infrastructure.external.MercadoPagoGateway;
import br.com.fiap.postech.soat.techchallenge.application.usercases.PaymentGatewayUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentGatewayService implements PaymentGatewayUseCase {

    private final MercadoPagoGateway mercadoPagoGateway;

    @Override
    public String qrDynamic(BigDecimal amount, UUID paymentId) {
        log.info("Generating dynamic QR code for payment ID: {}, amount: {}", paymentId, amount);
        return mercadoPagoGateway.qrDynamic(paymentId, amount);
    }
}
