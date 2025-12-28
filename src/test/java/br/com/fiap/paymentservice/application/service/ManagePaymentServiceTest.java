package br.com.fiap.paymentservice.application.service;

import br.com.fiap.paymentservice.application.dto.pagination.PagedResult;
import br.com.fiap.paymentservice.application.exceptions.NotFoundException;
import br.com.fiap.paymentservice.application.usercases.PaymentGatewayUseCase;
import br.com.fiap.paymentservice.application.usercases.PaymentOrderUseCase;
import br.com.fiap.paymentservice.domain.models.Payment;
import br.com.fiap.paymentservice.domain.models.PaymentStatus;
import br.com.fiap.paymentservice.infrastructure.persistence.payment.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ManagePaymentServiceTest {

    private PaymentRepository repository;
    private PaymentGatewayUseCase gateway;
    private PaymentOrderUseCase orderUseCase;
    private ManagePaymentService service;

    @BeforeEach
    void setup() {
        repository = mock(PaymentRepository.class);
        gateway = mock(PaymentGatewayUseCase.class);
        orderUseCase = mock(PaymentOrderUseCase.class);
        service = new ManagePaymentService(repository, gateway, orderUseCase);
    }

    @Test
    void create_shouldGeneratePaymentAndSave() {
        UUID orderId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(10.5);

        when(gateway.qrDynamic(eq(amount), any(UUID.class))).thenReturn("QR123");

        UUID paymentId = service.create(orderId, amount);

        assertThat(paymentId).isNotNull();

        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        verify(repository, times(1)).save(captor.capture());

        Payment saved = captor.getValue();
        assertThat(saved.getId()).isEqualTo(paymentId);
        assertThat(saved.getOrderId()).isEqualTo(orderId);
        assertThat(saved.getAmount()).isEqualTo(amount);
        assertThat(saved.getStatus()).isEqualTo(PaymentStatus.PENDING);
        assertThat(saved.getQrCode()).isEqualTo("QR123");
    }

    @Test
    void getById_shouldReturnPayment_whenFound() {
        UUID id = UUID.randomUUID();
        Payment p = new Payment(id, PaymentStatus.PENDING, UUID.randomUUID(), BigDecimal.ONE, "q");
        when(repository.findById(id)).thenReturn(Optional.of(p));

        Payment result = service.getById(id);

        assertThat(result).isSameAs(p);
    }

    @Test
    void getById_shouldThrowNotFound_whenMissing() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(id)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getByOrderId_shouldReturnPayment_whenFound() {
        UUID orderId = UUID.randomUUID();
        Payment p = new Payment(UUID.randomUUID(), PaymentStatus.PENDING, orderId, BigDecimal.ONE, "q");
        when(repository.findByOrderId(orderId)).thenReturn(Optional.of(p));

        Payment result = service.getByOrderId(orderId);

        assertThat(result).isSameAs(p);
    }

    @Test
    void getByOrderId_shouldThrowNotFound_whenMissing() {
        UUID orderId = UUID.randomUUID();
        when(repository.findByOrderId(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getByOrderId(orderId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void list_shouldCallFindAll_whenOrderIdNull() {
        PagedResult<Payment> paged = new PagedResult<>(List.of(), 0L, 0);
        when(repository.findAll(0, 10)).thenReturn(paged);

        PagedResult<Payment> res = service.list(null, 0, 10);

        assertThat(res).isSameAs(paged);
        verify(repository, times(1)).findAll(0, 10);
        verify(repository, never()).findByOrderId(any(), anyInt(), anyInt());
    }

    @Test
    void list_shouldCallFindByOrderId_whenOrderIdProvided() {
        UUID orderId = UUID.randomUUID();
        PagedResult<Payment> paged = new PagedResult<>(List.of(), 0L, 0);
        when(repository.findByOrderId(orderId, 1, 5)).thenReturn(paged);

        PagedResult<Payment> res = service.list(orderId, 1, 5);

        assertThat(res).isSameAs(paged);
        verify(repository, times(1)).findByOrderId(orderId, 1, 5);
    }

    @Test
    void pay_shouldUpdateStatusAndNotifyAndSave() {
        UUID id = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Payment payment = new Payment(id, PaymentStatus.PENDING, orderId, BigDecimal.TEN, "qr");
        when(repository.findById(id)).thenReturn(Optional.of(payment));

        service.pay(PaymentStatus.PAID, id);

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.PAID);
        verify(orderUseCase, times(1)).notifyOrderPayment(orderId, id, PaymentStatus.PAID.name());
        verify(repository, times(1)).save(payment);
    }

    @Test
    void pay_shouldCallNotifyEvenIfOrderIdNull_andSave() {
        UUID id = UUID.randomUUID();
        Payment payment = new Payment(id, PaymentStatus.PENDING, null, BigDecimal.TEN, "qr");
        when(repository.findById(id)).thenReturn(Optional.of(payment));

        service.pay(PaymentStatus.PAID, id);

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.PAID);
        verify(orderUseCase, times(1)).notifyOrderPayment(null, id, PaymentStatus.PAID.name());
        verify(repository, times(1)).save(payment);
    }

    @Test
    void pay_shouldPropagateExceptionFromNotify_andNotSave() {
        UUID id = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Payment payment = new Payment(id, PaymentStatus.PENDING, orderId, BigDecimal.TEN, "qr");
        when(repository.findById(id)).thenReturn(Optional.of(payment));
        doThrow(new RuntimeException("net error")).when(orderUseCase).notifyOrderPayment(orderId, id, PaymentStatus.PAID.name());

        assertThatThrownBy(() -> service.pay(PaymentStatus.PAID, id)).isInstanceOf(RuntimeException.class);

        // save should not be called because exception was thrown
        verify(repository, never()).save(any());
    }

}

