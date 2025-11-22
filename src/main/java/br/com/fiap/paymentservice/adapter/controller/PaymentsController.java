package br.com.fiap.paymentservice.adapter.controller;

import br.com.fiap.paymentservice.application.dto.request.CreatePaymentRequest;
import br.com.fiap.paymentservice.infrastructure.web.PaymentAPI;
import br.com.fiap.paymentservice.application.dto.response.PaymentResponse;
import br.com.fiap.paymentservice.adapter.prensenter.mapper.PaymentResponseMapper;
import br.com.fiap.paymentservice.domain.models.Payment;
import br.com.fiap.paymentservice.application.usercases.ManagePaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController implements PaymentAPI {

    private final ManagePaymentUseCase useCase;
    private final PaymentResponseMapper mapper;

    @Override
    public ResponseEntity<PaymentResponse> getById(UUID paymentId) {
        Payment payment = useCase.getById(paymentId);
        PaymentResponse response = mapper.toResponse(payment);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PaymentResponse> getByOrderId(UUID orderId) {
        Payment payment = useCase.getByOrderId(orderId);
        PaymentResponse response = mapper.toResponse(payment);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> createPayment(CreatePaymentRequest paymentRequest) {
        UUID paymentId = useCase.create(paymentRequest.orderId(), paymentRequest.totalAmount());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(paymentId)
                .toUri();

        return ResponseEntity.created(location).build();
    }


}
