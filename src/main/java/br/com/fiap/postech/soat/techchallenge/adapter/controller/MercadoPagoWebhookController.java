package br.com.fiap.postech.soat.techchallenge.adapter.controller;

import br.com.fiap.postech.soat.techchallenge.application.dto.request.CallbackMercadoPagoRequest;
import br.com.fiap.postech.soat.techchallenge.application.usercases.ManagePaymentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class MercadoPagoWebhookController {

    private final ManagePaymentUseCase paymentUseCase;

    @PostMapping
    public ResponseEntity<String> notify(@Valid @RequestBody CallbackMercadoPagoRequest request) {
        log.info("Received Mercado Pago webhook notification: {}", request);

        paymentUseCase.pay(request.status(), request.id());

        return ResponseEntity.status(HttpStatus.OK).body("Notification received.");
    }
}
