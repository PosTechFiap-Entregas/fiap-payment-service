package br.com.fiap.postech.soat.techchallenge.infrastructure.web;

import br.com.fiap.postech.soat.techchallenge.application.dto.request.CreateProductRequest;
import br.com.fiap.postech.soat.techchallenge.application.dto.request.UpdateProductRequest;
import br.com.fiap.postech.soat.techchallenge.application.dto.response.ProductResponse;
import br.com.fiap.postech.soat.techchallenge.domain.models.ProductCategory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/products")
public interface ProductAPI {

    @PostMapping
    ResponseEntity<Void> createProduct(@Valid @RequestBody CreateProductRequest request);

    @PutMapping("/{id}")
    ResponseEntity<Void> updateProduct(@PathVariable UUID id, @Valid @RequestBody UpdateProductRequest request);

    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id);

    @GetMapping
    ResponseEntity<List<ProductResponse>> getProducts(@RequestParam(required = false) ProductCategory category, @RequestParam(required = false) Boolean active);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable UUID id);

    @PatchMapping("/{id}/activate")
    ResponseEntity<Void> activateProduct(@PathVariable UUID id);

    @PatchMapping("/{id}/deactivate")
    ResponseEntity<Void> deactivateProduct(@PathVariable UUID id);
}

