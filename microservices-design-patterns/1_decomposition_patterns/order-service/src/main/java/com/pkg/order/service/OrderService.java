package com.pkg.order.service;

import com.pkg.order.model.Order;
import com.pkg.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order createOrder(Order order) {
        return repository.save(order);
    }

    public Optional<Order> getOrder(Long id) {
        return repository.findById(id);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        return repository.findById(id)
                .map(order -> {
                    order.setDescription(updatedOrder.getDescription());
                    order.setOrderDate(updatedOrder.getOrderDate());
                    order.setAmount(updatedOrder.getAmount());
                    return repository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}
