package br.com.fiap.paymentservice.application.service;

import br.com.fiap.paymentservice.application.dto.request.OrderPaymentNotificationRequest;
import br.com.fiap.paymentservice.infrastructure.external.OrdersWebhookClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentOrderServiceTest {

    private OrdersWebhookClient ordersWebhookClient;
    private PaymentOrderService service;

    @BeforeEach
    void setup() {
        ordersWebhookClient = mock(OrdersWebhookClient.class);
        service = new PaymentOrderService(ordersWebhookClient);
    }

    @Test
    void notifyOrderPayment_callsOrdersWebhookClient_withExpectedRequest() {
        UUID orderId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();
        String status = "PAID";

        service.notifyOrderPayment(orderId, paymentId, status);

        ArgumentCaptor<OrderPaymentNotificationRequest> captor = ArgumentCaptor.forClass(OrderPaymentNotificationRequest.class);
        verify(ordersWebhookClient, times(1)).postPaymentNotification(captor.capture());

        OrderPaymentNotificationRequest req = captor.getValue();
        assertEquals(status, req.status());
        assertEquals(orderId.toString(), req.orderId());
        assertEquals(paymentId.toString(), req.paymentId());
    }

    @Test
    void notifyOrderPayment_whenClientThrows_exceptionIsPropagated() {
        UUID orderId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();
        String status = "FAILED";

        doThrow(new RuntimeException("boom")).when(ordersWebhookClient).postPaymentNotification(any());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.notifyOrderPayment(orderId, paymentId, status));
        assertEquals("boom", ex.getMessage());
        verify(ordersWebhookClient, times(1)).postPaymentNotification(any());
    }
}
