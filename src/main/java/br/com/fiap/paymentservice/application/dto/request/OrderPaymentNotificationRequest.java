package br.com.fiap.paymentservice.application.dto.request;

public record OrderPaymentNotificationRequest(String status, String orderId, String paymentId) {
}
