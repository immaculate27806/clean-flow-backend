package com.auca.cleanflow.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auca.cleanflow.model.ServiceType;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, UUID> {

}