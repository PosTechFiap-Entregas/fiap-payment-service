package br.com.fiap.postech.soat.techchallenge.adapter.controller;

import br.com.fiap.postech.soat.techchallenge.infrastructure.web.CustomerAPI;
import br.com.fiap.postech.soat.techchallenge.application.dto.request.CreateCustomerRequest;
import br.com.fiap.postech.soat.techchallenge.application.dto.response.CustomerResponse;
import br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper.CustomerResponseMapper;
import br.com.fiap.postech.soat.techchallenge.domain.models.Customer;
import br.com.fiap.postech.soat.techchallenge.application.usercases.ManageCustomerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController implements CustomerAPI {

    private final ManageCustomerUseCase useCase;
    private final CustomerResponseMapper responseMapper;

    @Operation(summary = "Customer registration")
    @Override
    public ResponseEntity<CustomerResponse> createCustomer(CreateCustomerRequest customerRequest) {
        Customer customer = useCase.createCustomer(customerRequest.name(), customerRequest.cpf(), customerRequest.email(), customerRequest.phone());
        CustomerResponse response = responseMapper.toResponse(customer);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Search customer by ID")
    @Override
    public ResponseEntity<CustomerResponse> getById(UUID id) {
        Customer customer = useCase.getCustomerById(id);
        CustomerResponse response = responseMapper.toResponse(customer);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Search customer by CPF")
    @Override
    public ResponseEntity<CustomerResponse> getByCpf(String cpf) {
        Customer customer = useCase.getCustomerByCpf(cpf);
        CustomerResponse response = responseMapper.toResponse(customer);
        return ResponseEntity.ok(response);
    }
}
