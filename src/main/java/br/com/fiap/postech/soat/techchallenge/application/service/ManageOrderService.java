package br.com.fiap.postech.soat.techchallenge.application.service;

import br.com.fiap.postech.soat.techchallenge.adapter.gateway.OrderGateway;
import br.com.fiap.postech.soat.techchallenge.application.exceptions.NotFoundException;
import br.com.fiap.postech.soat.techchallenge.application.usercases.*;
import br.com.fiap.postech.soat.techchallenge.application.dto.command.CreateOrderCommand;
import br.com.fiap.postech.soat.techchallenge.domain.exceptions.InvalidEmailException;
import br.com.fiap.postech.soat.techchallenge.domain.exceptions.InvalidOrderStatusException;
import br.com.fiap.postech.soat.techchallenge.domain.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class ManageOrderService implements ManageOrderUseCase {

    private final OrderGateway orderGateway;
    private final ManageCustomerUseCase manageCustomerUseCase;
    private final ManageOrderItemUseCase orderItemUseCase;
    private final ManageProductUseCase productUseCase;
    private final ManagePaymentUseCase paymentUseCase;

    @Override
    public Order getOrderById(UUID orderId) {
        log.info("Fetching order with ID: {}", orderId);
        return orderGateway.findOrderById(orderId).orElseThrow(() -> new NotFoundException("Order not found."));
    }

    @Override
    public List<Order> getOrdersByFilters(OrderStatus status, UUID customerId) {
        log.info("Fetching orders with status: {} and customer ID: {}", status, customerId);
        return orderGateway.findOrdersByFilters(status, customerId);
    }

    @Override
    public Order createOrder(CreateOrderCommand createOrderCommand) {
        log.info("Creating order with command: {}", createOrderCommand);
        List<OrderItem> orderItems = createOrderCommand.items().stream()
                .map(orderItem -> {
                    Product product = productUseCase.getProductById(orderItem.productId());
                    return new OrderItem(UUID.randomUUID(), orderItem.quantity(), product);
                })
                .toList();

        int totalPedidos = orderGateway.findAll().size();
        int nextOrderNumber = totalPedidos + 1;

        UUID customerId = (createOrderCommand.customerId() == null) ? UUID.fromString("dc821e5c-894a-4bdf-bded-e97b2657cb5a") : createOrderCommand.customerId();
        Customer customer = manageCustomerUseCase.getCustomerById(customerId);
        Order order = new Order(UUID.randomUUID(), customer, OrderStatus.AWAITING_PAYMENT, createOrderCommand.observation(), nextOrderNumber, orderItems);

        orderGateway.save(order);
        orderItemUseCase.createOrderItems(orderItems, order);
        paymentUseCase.create(order);

        return order;
    }

    @Override
    public void updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        log.info("Updating order status for order ID: {} to status: {}", orderId, orderStatus);
        Order order = getOrderById(orderId);
        OrderStatus status = order.getStatus();

        switch (status) {
            case AWAITING_PAYMENT:
                order.setStatus(OrderStatus.RECEIVED);
                break;
            case RECEIVED:
                order.setStatus(OrderStatus.IN_PREPARATION);
                break;
            case IN_PREPARATION:
                order.setStatus(OrderStatus.READY);
                break;
//            case READY:
//                order.setStatus(OrderStatus.FINALIZED);
//                break;
        }
        orderGateway.save(order);
    }

    @Override
    public void deleteOrder(UUID orderID) {
    log.info("Deleting order with ID: {}", orderID);
        orderGateway.deleteById(orderID);
    }

    @Override
    public Order checkoutOrder(UUID orderId) {
        Order order = getOrderById(orderId);
        OrderStatus status = order.getStatus();
        if (status != OrderStatus.READY) {
            throw new InvalidOrderStatusException("Order with status " + status + " cannot be finalized.");
        }
        order.setStatus(OrderStatus.FINALIZED);
        orderGateway.save(order);
        return order;
    }
}
