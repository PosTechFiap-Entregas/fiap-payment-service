package br.com.fiap.paymentservice.adapter.prensenter.handler;

import java.util.List;

public record ValidationErrorResponse(int status, List<String> errors) {
}

