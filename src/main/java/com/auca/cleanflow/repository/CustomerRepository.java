package com.auca.cleanflow.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auca.cleanflow.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}