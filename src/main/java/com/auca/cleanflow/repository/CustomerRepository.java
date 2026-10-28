package com.auca.cleanflow.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.cleanflow.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT o.customer FROM LaundryOrder o WHERE o.attendant.id = :attendantId")
    List<Customer> findCustomersOfAttendant(@Param("attendantId") UUID attendantId);
}