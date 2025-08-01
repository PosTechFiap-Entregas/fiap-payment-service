package br.com.fiap.postech.soat.techchallenge.infrastructure.web;

import br.com.fiap.postech.soat.techchallenge.application.dto.request.CreateCustomerRequest;
import br.com.fiap.postech.soat.techchallenge.application.dto.response.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/customers")
public interface CustomerAPI {

    @PostMapping
    ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest customerRequest);

    @GetMapping("/{id}")
    ResponseEntity<CustomerResponse> getById(@PathVariable UUID id);

    @GetMapping
    ResponseEntity<CustomerResponse> getByCpf(@RequestParam String cpf);
}
