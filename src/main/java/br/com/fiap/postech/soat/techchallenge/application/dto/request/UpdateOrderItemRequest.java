package br.com.fiap.postech.soat.techchallenge.application.dto.request;

import java.util.List;

public record UpdateOrderItemRequest (
        Integer quantity,
        List<UpdateProductRequest> productRequests
) {
}
