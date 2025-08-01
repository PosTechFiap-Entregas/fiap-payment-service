package br.com.fiap.postech.soat.techchallenge.infrastructure.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MercadoPagoGateway {

    @Value("${mercado-pago.access-token}")
    private String ACCESS_TOKEN ;
    @Value("${mercado-pago.collector-id}")
    private String COLLECTOR_ID ;
    @Value("${mercado-pago.pos-id}")
    private String POS_ID;
    @Value("${mercado-pago.notification-url}")
    private String NOTIFICATION_URL;

    @Autowired
    private final RestTemplate restTemplate;

    public String qrDynamic(UUID paymentId, BigDecimal amount) {
        String url = String.format("https://api.mercadopago.com/instore/orders/qr/seller/collectors/%s/pos/%s/qrs",
                COLLECTOR_ID, POS_ID);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ACCESS_TOKEN);

        Map<String, Object> item = Map.of(
                "sku_number", paymentId.toString(),
                "title", "Pedido: " + paymentId,
                "description", "Pedido Número: " + paymentId,
                "category", "food",
                "unit_price", amount,
                "quantity", 1,
                "unit_measure", "unit",
                "total_amount", amount
        );

        Map<String, Object> body = Map.of(
                "external_reference", paymentId.toString(),
                "title", "Order " + paymentId,
                "description", "Order Number: " + paymentId,
                "notification_url", NOTIFICATION_URL + "/techchallenge/v1/webhook",
                "items", List.of(item),
                "total_amount", amount
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = new ObjectMapper().readTree(response.getBody());
                return root.path("qr_data").asText("");
            } else {
                // Log warning ou tratar retorno vazio
                return null;
            }
        } catch (HttpStatusCodeException e) {
            // Log detalhado (pode incluir e.getResponseBodyAsString())
            System.err.println("Erro HTTP: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            // Erro inesperado
            System.err.println("Erro ao gerar QR Code: " + e.getMessage());
            return null;
        }
    }

    public void updatePaymentStatus(String paymentId, String status) {
        // Implement the logic to update the payment status using Mercado Pago API
    }

    public String getPaymentStatus(String paymentId) {
        // Implement the logic to retrieve the payment status using Mercado Pago API
        return "payment-status"; // Placeholder for the actual payment status
    }

    public String getPaymentDetails(String paymentId) {
        // Implement the logic to retrieve payment details using Mercado Pago API
        return "payment-details"; // Placeholder for the actual payment details
    }
}
