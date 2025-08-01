package br.com.fiap.postech.soat.techchallenge.adapter.prensenter.handler;

import java.util.List;

public record ValidationErrorResponse(int status, List<String> errors) {
}

