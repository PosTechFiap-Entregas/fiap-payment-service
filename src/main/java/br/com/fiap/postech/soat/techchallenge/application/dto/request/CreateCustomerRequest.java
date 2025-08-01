package br.com.fiap.postech.soat.techchallenge.application.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CreateCustomerRequest(
        @NotBlank(message = "{customer.name.notblank}")
        String name,

        @NotBlank(message = "{customer.cpf.notblank}")
        @CPF(message = "{customer.cpf.invalid}")
        String cpf,

        @NotBlank(message = "{customer.email.notblank}")
        @Email(message = "{customer.email.invalid}")
        String email,

        @NotBlank(message = "{customer.phone.notblank}")
        @Digits(integer = 11, fraction = 0, message = "{customer.phone.invalid}")
        String phone
) {
}
