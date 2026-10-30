package com.auca.cleanflow.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.cleanflow.model.DeliveryNote;
import com.auca.cleanflow.model.LaundryOrder;

public interface DeliveryNoteRepository extends JpaRepository<DeliveryNote, UUID> {

    @Query("SELECT d FROM DeliveryNote d WHERE d.laundryOrder.customer.phone = :phone ORDER BY d.deliveredDate DESC")
    List<DeliveryNote> findNotesOfCustomer(@Param("phone") String phone);

    boolean existsByLaundryOrder(LaundryOrder laundryOrder);
}