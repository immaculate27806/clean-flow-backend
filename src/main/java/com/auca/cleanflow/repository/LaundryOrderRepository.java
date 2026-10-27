package com.auca.cleanflow.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auca.cleanflow.model.LaundryOrder;

public interface LaundryOrderRepository extends JpaRepository<LaundryOrder, UUID> {

}
