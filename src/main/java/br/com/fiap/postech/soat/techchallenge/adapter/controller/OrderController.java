package br.com.fiap.postech.soat.techchallenge.adapter.controller;

import br.com.fiap.postech.soat.techchallenge.adapter.prensenter.IdentificationPresenter;
import br.com.fiap.postech.soat.techchallenge.application.dto.response.OrderIdentificationResponse;
import br.com.fiap.postech.soat.techchallenge.infrastructure.web.OrderAPI;
import br.com.fiap.postech.soat.techchallenge.application.dto.request.CreateOrderRequest;
import br.com.fiap.postech.soat.techchallenge.application.dto.request.UpdateOrderRequest;
import br.com.fiap.postech.soat.techchallenge.application.dto.response.OrderResponse;
import br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper.OrderRequestMapper;
import br.com.fiap.postech.soat.techchallenge.adapter.prensenter.mapper.OrderResponseMapper;
import br.com.fiap.postech.soat.techchallenge.domain.models.Order;
import br.com.fiap.postech.soat.techchallenge.domain.models.OrderStatus;
import br.com.fiap.postech.soat.techchallenge.application.usercases.ManageOrderUseCase;
import br.com.fiap.postech.soat.techchallenge.application.dto.command.CreateOrderCommand;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController implements OrderAPI {

    private final ManageOrderUseCase orderUseCase;
    private final OrderRequestMapper requestMapper;
    private final OrderResponseMapper responseMapper;
    private final IdentificationPresenter presenter;

    @Operation(summary = "Order search by ID")
    @Override
    public ResponseEntity<OrderResponse> getOrderById(UUID id) {
        Order order = orderUseCase.getOrderById(id);
        OrderResponse response = responseMapper.toResponse(order);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registered order")
    @Override
    public ResponseEntity<Void> createOrder(CreateOrderRequest request) {

        CreateOrderCommand createOrderCommand = requestMapper.toCommand(request);

        Order order = orderUseCase.createOrder(createOrderCommand);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Update order status")
    @Override
    public ResponseEntity<Void> updateOrderStatusById(UUID orderId, UpdateOrderRequest orderRequest) {
        orderUseCase.updateOrderStatus(orderId, orderRequest.status());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search all orders")
    @Override
    public ResponseEntity<List<OrderResponse>> getOrders(OrderStatus status, UUID customerId) {
        List<Order> orders = orderUseCase.getOrdersByFilters(status, customerId);
        List<OrderResponse> responses = orders.stream().map(responseMapper::toResponse).toList();

        return ResponseEntity.ok(responses);
    }


    @Operation(summary = "Delete order")
    @Override
    public ResponseEntity<Void> deleteOrder(UUID id) {
        orderUseCase.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Checkout order")
    @Override
    public ResponseEntity<OrderIdentificationResponse> checkout(UUID id) {
        Order order = orderUseCase.checkoutOrder(id);
        return ResponseEntity.ok(presenter.toIdentification(order));
    }
}
