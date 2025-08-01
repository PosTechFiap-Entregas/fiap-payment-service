package br.com.fiap.postech.soat.techchallenge.application.usercases;

import br.com.fiap.postech.soat.techchallenge.domain.models.Product;
import br.com.fiap.postech.soat.techchallenge.domain.models.ProductCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ManageProductUseCase {
    Product getProductById(UUID productId);
    List<Product> getProducts(ProductCategory category, Boolean active);
    Product createProduct(String name, BigDecimal price, ProductCategory category, String description, String imageUrl);
    void updateProduct(UUID productId, String name, BigDecimal price, ProductCategory category, String description, String imageUrl);
    void deleteProduct(UUID productId);
    void activateProduct(UUID productId);
    void deactivateProduct(UUID productId);
}
