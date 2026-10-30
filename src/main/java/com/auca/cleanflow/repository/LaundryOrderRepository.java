package com.auca.cleanflow.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.cleanflow.model.Attendant;
import com.auca.cleanflow.model.LaundryOrder;
import com.auca.cleanflow.model.OrderStatus;

public interface LaundryOrderRepository extends JpaRepository<LaundryOrder, UUID> {

    List<LaundryOrder> findByStatusOrderByOrderDateAsc(OrderStatus status);

    List<LaundryOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);

    boolean existsByAttendantAndOrderDateAndStatusNot(Attendant attendant, LocalDate orderDate, OrderStatus status);

    @Query("SELECT o.status, COUNT(o) FROM LaundryOrder o GROUP BY o.status")
    List<Object[]> countOrdersByStatus();

    @Modifying
    @Query("UPDATE LaundryOrder o SET o.status = :cancelled WHERE o.attendant.id = :attendantId AND o.orderDate = :date AND o.status <> :delivered")
    int cancelOrdersOfDay(@Param("attendantId") UUID attendantId,
                          @Param("date") LocalDate date,
                          @Param("cancelled") OrderStatus cancelled,
                          @Param("delivered") OrderStatus delivered);
}