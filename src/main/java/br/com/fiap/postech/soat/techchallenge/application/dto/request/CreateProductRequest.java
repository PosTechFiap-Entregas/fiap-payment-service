package br.com.fiap.postech.soat.techchallenge.application.dto.request;

import br.com.fiap.postech.soat.techchallenge.domain.models.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "{product.name.notblank}") String name,
        @NotNull(message = "{product.price.notnull}") @Positive(message = "{product.price.positive}") BigDecimal price,
        @NotNull(message = "{product.category.notnull}") ProductCategory category,
        String description,
        @NotBlank(message = "{product.imageUrl.notblank}") String imageUrl

) {
}
