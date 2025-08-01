package br.com.fiap.postech.soat.techchallenge.adapter.controller;

import br.com.fiap.postech.soat.techchallenge.infrastructure.web.ProductAPI;
import br.com.fiap.postech.soat.techchallenge.application.dto.request.CreateProductRequest;
import br.com.fiap.postech.soat.techchallenge.application.dto.request.UpdateProductRequest;
import br.com.fiap.postech.soat.techchallenge.application.dto.response.ProductResponse;
import br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper.ProductResponseMapper;
import br.com.fiap.postech.soat.techchallenge.domain.models.Product;
import br.com.fiap.postech.soat.techchallenge.domain.models.ProductCategory;
import br.com.fiap.postech.soat.techchallenge.application.usercases.ManageProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController implements ProductAPI {

    private final ManageProductUseCase useCase;
    private final ProductResponseMapper mapper;

    @Operation(summary = "Product search by ID")
    @Override
    public ResponseEntity<ProductResponse> getProductById(UUID id) {
        Product product = useCase.getProductById(id);
        ProductResponse response = mapper.toResponse(product);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registered product")
    @Override
    public ResponseEntity<Void> createProduct(CreateProductRequest request) {
        Product product = useCase.createProduct(request.name(), request.price(), request.category(), request.description(), request.imageUrl());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Updated product")
    @Override
    public ResponseEntity<Void> updateProduct(UUID id, UpdateProductRequest request) {
        useCase.updateProduct(id, request.name(), request.price(), request.category(), request.description(), request.imageUrl());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search all products")
    @Override
    public ResponseEntity<List<ProductResponse>> getProducts(
            ProductCategory category, Boolean active) {

        List<Product> products = useCase.getProducts(category, active);

        List<ProductResponse> response = products
                .stream().map(mapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete product")
    @Override
    public ResponseEntity<Void> deleteProduct(UUID id) {
        useCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Enable product")
    @Override
    public ResponseEntity<Void> activateProduct(UUID id) {
        useCase.activateProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Disable product")
    @Override
    public ResponseEntity<Void> deactivateProduct(UUID id) {
        useCase.deactivateProduct(id);
        return ResponseEntity.noContent().build();
    }
}
