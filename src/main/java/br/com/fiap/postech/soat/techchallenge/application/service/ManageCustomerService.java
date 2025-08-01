package br.com.fiap.postech.soat.techchallenge.application.service;

import br.com.fiap.postech.soat.techchallenge.application.exceptions.CustomerAlreadyExistsException;
import br.com.fiap.postech.soat.techchallenge.application.exceptions.NotFoundException;
import br.com.fiap.postech.soat.techchallenge.adapter.gateway.ManageCustomerGateway;
import br.com.fiap.postech.soat.techchallenge.domain.models.Customer;
import br.com.fiap.postech.soat.techchallenge.application.usercases.ManageCustomerUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class ManageCustomerService implements ManageCustomerUseCase {

    private final ManageCustomerGateway manageCustomerGateway;

    @Override
    public Customer getCustomerById(UUID customerId) {
        log.info("Fetching customer with ID: {}", customerId);
        return manageCustomerGateway.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found."));
    }

    @Override
    public Customer getCustomerByCpf(String cpf) {
    log.info("Fetching customer with CPF: {}", cpf);
        return manageCustomerGateway.findByCpf(cpf).orElseThrow(() -> new NotFoundException("Customer not found."));
    }

    @Override
    public Customer createCustomer(String name, String cpf, String email, String phone) {
        log.info("Creating customer with CPF: {}", cpf);

        Optional<Customer> existingCustomer = manageCustomerGateway.findByCpf(cpf);
        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with CPF " + cpf + " already exists.");
        }

        Customer customer = new Customer(UUID.randomUUID(), name, cpf, email, phone);
        manageCustomerGateway.save(customer);
        return customer;
    }
}
