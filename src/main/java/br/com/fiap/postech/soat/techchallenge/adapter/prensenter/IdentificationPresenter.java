package br.com.fiap.postech.soat.techchallenge.adapter.prensenter;

import br.com.fiap.postech.soat.techchallenge.application.dto.response.OrderIdentificationResponse;
import br.com.fiap.postech.soat.techchallenge.domain.models.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IdentificationPresenter {

    default OrderIdentificationResponse toIdentification(Order order){
        String name = order.getCustomer().getName();
        String identification = "identif-"+ name.replaceAll(" ", "-") + "-orderNumber-" + order.getNumber();
        return new OrderIdentificationResponse(identification);
    }
}
