package br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.product;

import br.com.fiap.postech.soat.techchallenge.domain.models.ProductCategory;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<ProductEntity> hasCategory(ProductCategory category) {
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }

    public static Specification<ProductEntity> isActive(Boolean active) {
        return (root, query, cb) -> cb.equal(root.get("active"), active);
    }
}
