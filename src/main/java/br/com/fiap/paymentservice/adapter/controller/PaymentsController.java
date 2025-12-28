package br.com.fiap.paymentservice.adapter.controller;

import br.com.fiap.paymentservice.application.dto.pagination.PagedResult;
import br.com.fiap.paymentservice.application.dto.request.CreatePaymentRequest;
import br.com.fiap.paymentservice.infrastructure.web.PaymentAPI;
import br.com.fiap.paymentservice.application.dto.response.PaymentResponse;
import br.com.fiap.paymentservice.adapter.prensenter.mapper.PaymentResponseMapper;
import br.com.fiap.paymentservice.domain.models.Payment;
import br.com.fiap.paymentservice.application.usercases.ManagePaymentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController implements PaymentAPI {

    private final ManagePaymentUseCase useCase;
    private final PaymentResponseMapper mapper;

    @Override
    public ResponseEntity<PagedResult<PaymentResponse>> list(UUID orderId, int page, int limit) {
        PagedResult<Payment> payments = useCase.list(orderId, page, limit);
        List<PaymentResponse> responses = payments.getContent().stream().map(mapper::toResponse).collect(Collectors.toList());
        PagedResult<PaymentResponse> result = new PagedResult<>(responses, payments.getTotalElements(), payments.getPage());
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<PaymentResponse> getById(UUID paymentId) {
        Payment payment = useCase.getById(paymentId);
        PaymentResponse response = mapper.toResponse(payment);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> createPayment(@Valid CreatePaymentRequest paymentRequest) {
        UUID paymentId = useCase.create(paymentRequest.orderId(), paymentRequest.totalAmount());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(paymentId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
