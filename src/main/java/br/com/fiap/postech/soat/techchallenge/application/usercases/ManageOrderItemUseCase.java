package br.com.fiap.postech.soat.techchallenge.application.usercases;

import br.com.fiap.postech.soat.techchallenge.domain.models.Order;
import br.com.fiap.postech.soat.techchallenge.domain.models.OrderItem;

import java.util.List;

public interface ManageOrderItemUseCase {
    void createOrderItems(List<OrderItem> items, Order order);
}
