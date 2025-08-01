package br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper;

import br.com.fiap.postech.soat.techchallenge.infrastructure.persistence.customer.CustomerEntity;
import br.com.fiap.postech.soat.techchallenge.domain.models.Customer;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    default Optional<Customer> toDomain(Optional<CustomerEntity> optional) {
        if (optional.isEmpty()) {
            return Optional.empty();
        }

        CustomerEntity entity = optional.get();
        Customer customer = new Customer(entity.getId(), entity.getName(), entity.getCpf(), entity.getEmail(), entity.getPhone());
        return Optional.of(customer);
    }

    default CustomerEntity toEntity(Customer customer){
        if (customer == null) return null;
        return new CustomerEntity(customer.getId(), customer.getName(), customer.getCpf(), customer.getEmail(), customer.getPhone());
    }
}
