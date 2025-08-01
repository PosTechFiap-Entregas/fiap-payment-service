package br.com.fiap.postech.soat.techchallenge.adapter.gateway;

import br.com.fiap.postech.soat.techchallenge.domain.models.Product;
import br.com.fiap.postech.soat.techchallenge.domain.models.ProductCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductGateway {
    void save(Product product);
    Optional<Product> findById(UUID id);
    void deleteById(UUID id);
    List<Product> findByFilters(ProductCategory category, Boolean active);
}
