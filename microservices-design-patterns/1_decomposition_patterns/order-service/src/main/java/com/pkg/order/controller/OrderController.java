package com.pkg.order.controller;


import com.pkg.order.model.Order;
import com.pkg.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order createOrder(@Valid @RequestBody Order order) {
        return service.createOrder(order);
    }

    @GetMapping("/{id}")
    public Optional<Order> getOrder(@PathVariable Long id) {
        return service.getOrder(id);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        return service.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
        return "Order with ID " + id + " deleted successfully.";
    }
}
