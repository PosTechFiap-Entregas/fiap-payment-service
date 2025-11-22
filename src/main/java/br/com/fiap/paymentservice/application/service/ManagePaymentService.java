package br.com.fiap.paymentservice.application.service;

import br.com.fiap.paymentservice.application.exceptions.NotFoundException;
import br.com.fiap.paymentservice.domain.models.Payment;
import br.com.fiap.paymentservice.domain.models.PaymentStatus;
import br.com.fiap.paymentservice.application.usercases.ManagePaymentUseCase;
import br.com.fiap.paymentservice.application.usercases.PaymentGatewayUseCase;
import br.com.fiap.paymentservice.infrastructure.persistence.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class ManagePaymentService implements ManagePaymentUseCase {

    private final PaymentRepository repository;
    private final PaymentGatewayUseCase paymentGatewayUseCase;

    @Override
    public UUID create(UUID orderId, BigDecimal amount) {
        log.info("Creating payment for order: {}", orderId);

        // TODO: verificar via REST existencia da orderId antes de criar o pagamento

        UUID paymentId = UUID.randomUUID();
        String qrCode = paymentGatewayUseCase.qrDynamic(amount, paymentId);
        Payment payment = new Payment(paymentId, PaymentStatus.PENDING, orderId, amount, qrCode);

        repository.save(payment);

        return paymentId;
    }

    @Override
    public void pay(PaymentStatus status, UUID id) {
        log.info("Processing payments by ID: {}", id);

        Payment payment = getById(id);
        payment.setStatus(status);
        repository.save(payment);

        // Atualizar order via REST:
//        paymentOrderUseCase.updateOrderByPayment(payment.getId());
    }

    @Override
    public Payment getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Payment not found."));
    }

    @Override
    public Payment getByOrderId(UUID orderId) {
        log.info("Retrieving payment by order ID: {}", orderId);
        return repository.findByOrderId(orderId).orElseThrow(() -> new NotFoundException("Payment not found for order ID: " + orderId));
    }
}
