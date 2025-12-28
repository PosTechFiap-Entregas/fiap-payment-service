package br.com.fiap.paymentservice.application.service;

import br.com.fiap.paymentservice.application.dto.request.OrderPaymentNotificationRequest;
import br.com.fiap.paymentservice.application.usercases.PaymentOrderUseCase;
import br.com.fiap.paymentservice.infrastructure.external.OrdersWebhookClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentOrderService implements PaymentOrderUseCase {

    private final OrdersWebhookClient ordersWebhookClient;

    @Override
    public void notifyOrderPayment(UUID orderId, UUID paymentId, String status) {
        try {
            OrderPaymentNotificationRequest request = new OrderPaymentNotificationRequest(status, orderId.toString(), paymentId.toString());
            log.info("Notifying orders service about payment: {}", request);
            ordersWebhookClient.postPaymentNotification(request);
        } catch (Exception e) {
            log.error("Error notifying orders service for payment {}", paymentId, e);
            // Depending on requirements we could add retries, queueing, or error handling here.
            throw e;
        }
    }
}
