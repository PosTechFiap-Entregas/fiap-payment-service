package br.com.fiap.postech.soat.techchallenge.application.service;

import br.com.fiap.postech.soat.techchallenge.application.exceptions.NotFoundException;
import br.com.fiap.postech.soat.techchallenge.domain.models.Order;
import br.com.fiap.postech.soat.techchallenge.domain.models.Payment;
import br.com.fiap.postech.soat.techchallenge.domain.models.PaymentStatus;
import br.com.fiap.postech.soat.techchallenge.application.usercases.ManagePaymentUseCase;
import br.com.fiap.postech.soat.techchallenge.application.usercases.PaymentGatewayUseCase;
import br.com.fiap.postech.soat.techchallenge.application.usercases.PaymentOrderUseCase;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class ManagePaymentService implements ManagePaymentUseCase {

    private final PaymentRepository repository;
    private final PaymentGatewayUseCase paymentGatewayUseCase;
    private final PaymentOrderUseCase paymentOrderUseCase;

    @Override
    public void create(Order order) {
        log.info("Creating payment for order: {}", order.getId());
        UUID paymentId = UUID.randomUUID();
        String qrCode = paymentGatewayUseCase.qrDynamic(order.getTotalAmount(), paymentId);
        Payment payment = new Payment(paymentId, PaymentStatus.PENDING, order, qrCode);

        repository.save(payment);
    }

    @Override
    public void update(PaymentStatus status, UUID id) {
        log.info("Updating payment status for ID and status: {}, {}", id, status);

        Payment payment = getById(id);
        payment.setStatus(status);
        repository.save(payment);
    }

    @Override
    public void pay(PaymentStatus status, UUID id) {
        log.info("Processing payments by ID: {}", id);

        Payment payment = getById(id);
        payment.setStatus(status);
        repository.save(payment);

        paymentOrderUseCase.updateOrderByPayment(payment.getId());
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
