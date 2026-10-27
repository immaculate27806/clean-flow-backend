package com.auca.cleanflow.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.cleanflow.model.Customer;
import com.auca.cleanflow.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public String saveCustomer(Customer customer) {
        customerRepository.save(customer);
        return "Customer saved successfully";
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    public String updateCustomer(UUID id, Customer newData) {
        Optional<Customer> found = customerRepository.findById(id);
        if (found.isEmpty()) {
            return "The customer with that id does not exist";
        }
        Customer customer = found.get();
        customer.setFirstName(newData.getFirstName());
        customer.setLastName(newData.getLastName());
        customer.setPhone(newData.getPhone());
        customer.setEmail(newData.getEmail());
        customer.setAddress(newData.getAddress());
        customerRepository.save(customer);
        return "Customer updated successfully";
    }

    public String deleteCustomer(UUID id) {
        Optional<Customer> found = customerRepository.findById(id);
        if (found.isEmpty()) {
            return "The customer with that id does not exist";
        }
        customerRepository.deleteById(id);
        return "Customer deleted successfully";
    }

    public List<Customer> getCustomersByLastName(String lastName) {
        return customerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
}