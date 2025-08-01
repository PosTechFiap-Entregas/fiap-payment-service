package br.com.fiap.postech.soat.techchallenge.application.service;

import br.com.fiap.postech.soat.techchallenge.application.exceptions.NotFoundException;
import br.com.fiap.postech.soat.techchallenge.domain.exceptions.InvalidStatusException;
import br.com.fiap.postech.soat.techchallenge.domain.models.Order;
import br.com.fiap.postech.soat.techchallenge.domain.models.OrderStatus;
import br.com.fiap.postech.soat.techchallenge.domain.models.Payment;
import br.com.fiap.postech.soat.techchallenge.domain.models.PaymentStatus;
import br.com.fiap.postech.soat.techchallenge.application.usercases.PaymentOrderUseCase;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.order.OrderRepository;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentOrderService implements PaymentOrderUseCase {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;


    @Override
    public void updateOrderByPayment(UUID paymentId) {
        log.info("Updating order by payment ID: {}", paymentId);

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("Payment not found for ID: " + paymentId));
        if (payment.getStatus() != PaymentStatus.PAID) {
            throw new InvalidStatusException("Payment is not in PAID status.");
        }

        UUID orderId = payment.getOrder().getId();
        Order order = orderRepository.findOrderById(orderId).orElseThrow(() -> new NotFoundException("Order not found for ID: " + orderId));
        OrderStatus status = order.getStatus();

        if (status != OrderStatus.AWAITING_PAYMENT) {
            throw new NotFoundException("Order not in AWAITING_PAYMENT status.");
        }

        order.setStatus(OrderStatus.RECEIVED);
        orderRepository.save(order);
    }
}
