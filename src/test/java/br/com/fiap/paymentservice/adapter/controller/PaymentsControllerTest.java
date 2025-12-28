package br.com.fiap.paymentservice.adapter.controller;

import br.com.fiap.paymentservice.adapter.prensenter.mapper.PaymentResponseMapper;
import br.com.fiap.paymentservice.application.dto.pagination.PagedResult;
import br.com.fiap.paymentservice.application.dto.request.CreatePaymentRequest;
import br.com.fiap.paymentservice.application.dto.response.PaymentResponse;
import br.com.fiap.paymentservice.domain.models.Payment;
import br.com.fiap.paymentservice.domain.models.PaymentStatus;
import br.com.fiap.paymentservice.application.usercases.ManagePaymentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.mockito.MockedStatic;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentsControllerTest {

    private ManagePaymentUseCase useCase;
    private PaymentResponseMapper mapper;
    private PaymentsController controller;

    @BeforeEach
    void setup() {
        useCase = mock(ManagePaymentUseCase.class);
        mapper = mock(PaymentResponseMapper.class);
        controller = new PaymentsController(useCase, mapper);
    }

    @Test
    void list_returnsPagedResponse() {
        UUID orderId = UUID.randomUUID();
        Payment p = new Payment(UUID.randomUUID(), PaymentStatus.PENDING, orderId, BigDecimal.ONE, "qr");
        PagedResult<Payment> page = new PagedResult<>(List.of(p), 1, 0);
        when(useCase.list(orderId, 0, 10)).thenReturn(page);

        PaymentResponse resp = new PaymentResponse(p.getId(), p.getOrderId(), p.getQrCode(), p.getStatus());
        when(mapper.toResponse(p)).thenReturn(resp);

        ResponseEntity<PagedResult<PaymentResponse>> result = controller.list(orderId, 0, 10);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getContent().size());
        assertEquals(resp, result.getBody().getContent().get(0));
    }

    @Test
    void getById_returnsPaymentResponse() {
        UUID id = UUID.randomUUID();
        Payment p = new Payment(id, PaymentStatus.PENDING, UUID.randomUUID(), BigDecimal.ONE, "qr");
        when(useCase.getById(id)).thenReturn(p);
        PaymentResponse resp = new PaymentResponse(p.getId(), p.getOrderId(), p.getQrCode(), p.getStatus());
        when(mapper.toResponse(p)).thenReturn(resp);

        ResponseEntity<PaymentResponse> result = controller.getById(id);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(resp, result.getBody());
    }

    @Test
    void createPayment_returnsCreatedWithLocation() {
        UUID paymentId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        when(useCase.create(orderId, BigDecimal.valueOf(100))).thenReturn(paymentId);

        CreatePaymentRequest req = new CreatePaymentRequest(orderId, BigDecimal.valueOf(100));
        // Mock ServletUriComponentsBuilder.fromCurrentRequest() to avoid relying on ServletRequestAttributes
        try (MockedStatic<ServletUriComponentsBuilder> mocked = mockStatic(ServletUriComponentsBuilder.class)) {
            ServletUriComponentsBuilder builderMock = mock(ServletUriComponentsBuilder.class);
            mocked.when(ServletUriComponentsBuilder::fromCurrentRequest).thenReturn(builderMock);

            when(builderMock.path("/{id}")).thenReturn(builderMock);
            UriComponents uc = UriComponentsBuilder.fromPath("/payments/{id}").buildAndExpand(paymentId);
            when(builderMock.buildAndExpand(paymentId)).thenReturn(uc);

            ResponseEntity<Void> res = controller.createPayment(req);
            assertEquals(201, res.getStatusCode().value());
        }
    }
}
