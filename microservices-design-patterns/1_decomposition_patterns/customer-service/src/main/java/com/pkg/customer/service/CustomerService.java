package com.pkg.customer.service;

import com.pkg.customer.model.Customer;
import com.pkg.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }

    public Optional<Customer> getCustomer(Long id) {
        return repository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return repository.findById(id)
                .map(customer -> {
                    customer.setName(updatedCustomer.getName());
                    customer.setEmail(updatedCustomer.getEmail());
                    return repository.save(customer);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }
}
