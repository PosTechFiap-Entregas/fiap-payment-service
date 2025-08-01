package br.com.fiap.postech.soat.techchallenge.application.dto.response;

public record OrderItemResponse(
        ProductResponse product,
        Integer quantity
) {
}
