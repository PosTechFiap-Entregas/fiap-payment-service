package br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.product;

import br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper.ProductMapper;
import br.com.fiap.postech.soat.techchallenge.domain.models.Product;
import br.com.fiap.postech.soat.techchallenge.domain.models.ProductCategory;
import br.com.fiap.postech.soat.techchallenge.adapter.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepository implements ProductGateway {
    private final JpaProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public void save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        repository.save(entity);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> findByFilters(ProductCategory category, Boolean active) {
        Specification<ProductEntity> spec = Specification.where(null);

        if (category != null) {
            spec = spec.and(ProductSpecification.hasCategory(category));
        }

        if (active != null) {
            spec = spec.and(ProductSpecification.isActive(active));
        }

        List<ProductEntity> entities = repository.findAll(spec);

        List<Product> products = entities.stream().map(mapper::toDomain).collect(Collectors.toList());

        return products;
    }
}
