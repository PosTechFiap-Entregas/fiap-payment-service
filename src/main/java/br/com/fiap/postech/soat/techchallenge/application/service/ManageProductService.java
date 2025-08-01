package br.com.fiap.postech.soat.techchallenge.application.service;

import br.com.fiap.postech.soat.techchallenge.application.exceptions.NotFoundException;
import br.com.fiap.postech.soat.techchallenge.application.usercases.ManageProductUseCase;
import br.com.fiap.postech.soat.techchallenge.domain.models.Product;
import br.com.fiap.postech.soat.techchallenge.domain.models.ProductCategory;
import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class ManageProductService implements ManageProductUseCase {
    private final ProductRepository productRepository;

    @Override
    public Product getProductById(UUID productId) {
        log.info("Fetching product with ID: {}", productId);
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found."));
    }

    @Override
    public List<Product> getProducts(ProductCategory category, Boolean active) {
        log.info("Fetching products with category: {} and active status: {}", category, active);
        return productRepository.findByFilters(category, active);
    }

    @Override
    public Product createProduct(String name, BigDecimal price, ProductCategory category, String description, String imageUrl) {
        log.info("Creating product with name: {}, price: {}, category: {}, description: {}, imageUrl: {}", name, price, category, description, imageUrl);
        Product product = new Product(UUID.randomUUID(), name, price, category, description, true, imageUrl);
        productRepository.save(product);
        return product;
    }

    @Override
    public void updateProduct(UUID productId, String name, BigDecimal price, ProductCategory category, String description, String imageUrl) {
        log.info("Updating product with ID: {}, name: {}, price: {}, category: {}, description: {}, imageUrl: {}", productId, name, price, category, description, imageUrl);
        Product product = getProductById(productId);
        product.update(name, price, category, description, imageUrl);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID productId) {
        log.info("Deleting product with ID: {}", productId);
        productRepository.deleteById(productId);
    }

    @Override
    public void activateProduct(UUID productId) {
        log.info("Activating product with ID: {}", productId);
        Product product = getProductById(productId);
        product.activate();
        productRepository.save(product);
    }

    @Override
    public void deactivateProduct(UUID productId) {
        log.info("Deactivating product with ID: {}", productId);
        Product product = getProductById(productId);
        product.deactivate();
        productRepository.save(product);
    }
}
