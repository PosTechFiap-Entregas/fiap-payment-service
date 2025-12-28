package br.com.fiap.paymentservice.infrastructure.external;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MercadoPagoGatewayTest {

    private RestTemplate restTemplate;
    private MercadoPagoGateway gateway;

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        gateway = new MercadoPagoGateway(restTemplate);
        ReflectionTestUtils.setField(gateway, "ACCESS_TOKEN", "token");
        ReflectionTestUtils.setField(gateway, "COLLECTOR_ID", "collector");
        ReflectionTestUtils.setField(gateway, "POS_ID", "pos");
        ReflectionTestUtils.setField(gateway, "NOTIFICATION_URL", "http://callback.local");
    }

    @Test
    void qrDynamic_success_returnsQrData() {
        String json = "{\"qr_data\":\"SOMEQR\"}";
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(new ResponseEntity<>(json, HttpStatus.OK));

        String qr = gateway.qrDynamic(UUID.randomUUID(), BigDecimal.ONE);
        assertEquals("SOMEQR", qr);
    }

    @Test
    void qrDynamic_non2xx_orEmptyBody_returnsNull() {
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));
        String qr = gateway.qrDynamic(UUID.randomUUID(), BigDecimal.ONE);
        assertNull(qr);
    }

    @Test
    void qrDynamic_httpException_returnsNull() {
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class))).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        String qr = gateway.qrDynamic(UUID.randomUUID(), BigDecimal.ONE);
        assertNull(qr);
    }
}

