package br.com.fiap.paymentservice.infrastructure.external;

import br.com.fiap.paymentservice.application.dto.request.OrderPaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Log4j2
public class OrdersWebhookClient {

    private final RestTemplate restTemplate;

    @Value("${orders-service.host}")
    private String host;

    @Value("${orders-service.context-path}")
    private String contextPath;

    @Value("${orders-service.webhook-path}")
    private String webhookPath;

    public void postPaymentNotification(OrderPaymentNotificationRequest request) {
        String url = buildUrl();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<OrderPaymentNotificationRequest> entity = new HttpEntity<>(request, headers);

            log.info("Posting payment notification to orders service at {} with body {}", url, request);
            restTemplate.postForEntity(url, entity, Void.class);
        } catch (RestClientException e) {
            log.error("Failed to call orders webhook at {}", url, e);
            throw e;
        }
    }

    private String buildUrl() {
        String base = (host == null ? "" : host.trim());
        String cp = (contextPath == null ? "" : contextPath.trim());
        String wp = (webhookPath == null ? "" : webhookPath.trim());

        // Normalize segments to avoid double slashes
        if (base.endsWith("/")) base = base.substring(0, base.length() - 1);
        if (!cp.startsWith("/")) cp = "/" + cp;
        if (cp.endsWith("/")) cp = cp.substring(0, cp.length() - 1);
        if (!wp.startsWith("/")) wp = "/" + wp;

        return base + cp + wp;
    }
}

