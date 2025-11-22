package br.com.fiap.paymentservice.domain.models;

import br.com.fiap.paymentservice.domain.exceptions.InvalidStatusException;

import java.math.BigDecimal;
import java.util.UUID;

public class Payment {

    private UUID id;
    private PaymentStatus status;
    private UUID orderId;
    private String qrCode;
    private BigDecimal amount;

    public Payment(UUID id, PaymentStatus status, UUID orderId, BigDecimal amount, String qrCode) {
        this.setId(id);
        this.setStatus(status);
        this.setOrderId(orderId);
        this.setAmount(amount);
        this.setQrCode(qrCode);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        if (this.status != null && !this.status.verifyTransition(status)) {
            throw new InvalidStatusException("Invalid status transition from " + this.status + " to " + status);
        }

        this.status = status;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void update(PaymentStatus status) {
        this.setStatus(status);
    }

    public void updateQr(String qr) {
        this.setQrCode(qr);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
