package br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper;

import br.com.fiap.postech.soat.techchallenge.application.dto.response.CustomerResponse;
import br.com.fiap.postech.soat.techchallenge.domain.models.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerResponseMapper {
    default CustomerResponse toResponse(Customer customer){
        if (customer == null) return null;
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getCpf(), customer.getEmail(), customer.getPhone());
    }
}
