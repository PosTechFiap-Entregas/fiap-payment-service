package br.com.fiap.paymentservice.infrastructure.web;

import br.com.fiap.paymentservice.application.dto.request.CreatePaymentRequest;
import br.com.fiap.paymentservice.application.dto.response.PaymentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/payments")
public interface PaymentAPI {

    @GetMapping
    ResponseEntity<PaymentResponse> getByOrderId(@RequestParam UUID orderId);

    @GetMapping("/{paymentId}")
    ResponseEntity<PaymentResponse> getById(@PathVariable UUID paymentId);

    @PostMapping
    ResponseEntity<Void> createPayment(@Valid @RequestBody CreatePaymentRequest paymentRequest);



}
