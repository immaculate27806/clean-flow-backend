package com.auca.cleanflow.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.auca.cleanflow.model.ServiceType;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, UUID> {

    @Query("SELECT s FROM ServiceType s WHERE s.attendants IS EMPTY")
    List<ServiceType> findUnusedServiceTypes();
}