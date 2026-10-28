package com.auca.cleanflow.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.auca.cleanflow.model.Station;

public interface StationRepository extends JpaRepository<Station, UUID> {

    @Query("SELECT s FROM Station s WHERE NOT EXISTS (SELECT a FROM Attendant a WHERE a.station = s) ORDER BY s.stationNumber")
    List<Station> findFreeStations();
}