package com.auca.cleanflow.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auca.cleanflow.model.Attendant;

public interface AttendantRepository extends JpaRepository<Attendant, UUID> {

    @Query("SELECT a FROM Attendant a JOIN a.serviceTypes s WHERE LOWER(s.name) = LOWER(:name)")
    List<Attendant> findByServiceTypeName(@Param("name") String name);
}