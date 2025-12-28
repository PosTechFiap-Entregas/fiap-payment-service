package br.com.fiap.paymentservice.adapter.controller;

import br.com.fiap.paymentservice.application.dto.request.CallbackMercadoPagoRequest;
import br.com.fiap.paymentservice.application.usercases.ManagePaymentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MercadoPagoWebhookControllerTest {

    private ManagePaymentUseCase useCase;
    private MercadoPagoWebhookController controller;

    @BeforeEach
    void setup() {
        useCase = mock(ManagePaymentUseCase.class);
        controller = new MercadoPagoWebhookController(useCase);
    }

    @Test
    void notify_invokesUseCase_andReturnsOk() {
        UUID id = UUID.randomUUID();
        CallbackMercadoPagoRequest req = new CallbackMercadoPagoRequest(null, id);
        // call
        ResponseEntity<String> res = controller.notify(req);
        // verify
        verify(useCase, times(1)).pay(null, id);
        assertEquals(HttpStatusCode.valueOf(200), res.getStatusCode());
        assertEquals("Notification received.", res.getBody());
    }
}
