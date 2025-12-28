package br.com.fiap.paymentservice.infrastructure.external;

import br.com.fiap.paymentservice.application.dto.request.OrderPaymentNotificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrdersWebhookClientTest {

    private RestTemplate restTemplate;
    private OrdersWebhookClient client;

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        client = new OrdersWebhookClient(restTemplate);
        ReflectionTestUtils.setField(client, "host", "http://orders.local");
        ReflectionTestUtils.setField(client, "contextPath", "api/v1");
        ReflectionTestUtils.setField(client, "webhookPath", "webhook/payment");
    }

    @Test
    void postPaymentNotification_success_invokesRestTemplate() {
        doReturn(null).when(restTemplate).postForEntity(any(String.class), any(), any(Class.class));

        client.postPaymentNotification(new OrderPaymentNotificationRequest(null, null, null));

        verify(restTemplate, times(1)).postForEntity(eq("http://orders.local/api/v1/webhook/payment"), any(), eq(Void.class));
    }

    @Test
    void postPaymentNotification_restClientException_isPropagated() {
        doThrow(new RestClientException("fail")).when(restTemplate).postForEntity(any(String.class), any(), any(Class.class));

        try {
            client.postPaymentNotification(new OrderPaymentNotificationRequest(null, null, null));
            throw new AssertionError("expected exception");
        } catch (RestClientException e) {
            // expected
        }

        verify(restTemplate, times(1)).postForEntity(any(String.class), any(), any(Class.class));
    }
}
