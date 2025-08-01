package br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper;

import br.com.fiap.postech.soat.techchallenge.application.dto.response.ProductResponse;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.product.ProductEntity;
import br.com.fiap.postech.soat.techchallenge.domain.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);

    ProductResponse toResponse(Product domain);
}
