package com.pkg.customer.controller;

import com.pkg.customer.model.Customer;
import com.pkg.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return service.createCustomer(customer);
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomer(@PathVariable Long id) {
        return service.getCustomer(id);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        return service.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
        return "Customer with ID " + id + " deleted successfully.";
    }
}
