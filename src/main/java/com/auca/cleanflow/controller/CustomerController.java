package com.auca.cleanflow.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auca.cleanflow.model.Customer;
import com.auca.cleanflow.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Customer customer) {
        String message = customerService.saveCustomer(customer);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAll() {
        List<Customer> list = customerService.getAllCustomers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Customer>> getByLastName(@RequestParam String lastName) {
        List<Customer> list = customerService.getCustomersByLastName(lastName);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/of-attendant/{attendantId}")
    public ResponseEntity<Object> getOfAttendant(@PathVariable UUID attendantId) {
        if (!customerService.attendantExists(attendantId)) {
            return new ResponseEntity<>("The attendant with that id does not exist", HttpStatus.NOT_FOUND);
        }
        List<Customer> list = customerService.getCustomersOfAttendant(attendantId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Customer> found = customerService.getCustomerById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The customer with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Customer customer) {
        String message = customerService.updateCustomer(id, customer);
        if (message.equals("Customer updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = customerService.deleteCustomer(id);
        if (message.equals("Customer deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}