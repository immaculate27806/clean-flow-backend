package com.auca.cleanflow.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auca.cleanflow.model.Attendant;
import com.auca.cleanflow.model.LaundryOrder;
import com.auca.cleanflow.model.OrderStatus;

public interface LaundryOrderRepository extends JpaRepository<LaundryOrder, UUID> {

    List<LaundryOrder> findByStatusOrderByOrderDateAsc(OrderStatus status);

    List<LaundryOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);

    boolean existsByAttendantAndOrderDateAndStatusNot(Attendant attendant, LocalDate orderDate, OrderStatus status);
}