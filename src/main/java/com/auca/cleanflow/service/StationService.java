package com.auca.cleanflow.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.cleanflow.model.Station;
import com.auca.cleanflow.repository.StationRepository;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public String saveStation(Station station) {
        stationRepository.save(station);
        return "Station saved successfully";
    }

    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    public Optional<Station> getStationById(UUID id) {
        return stationRepository.findById(id);
    }

    public String updateStation(UUID id, Station newData) {
        Optional<Station> found = stationRepository.findById(id);
        if (found.isEmpty()) {
            return "The station with that id does not exist";
        }
        Station station = found.get();
        station.setStationNumber(newData.getStationNumber());
        station.setName(newData.getName());
        stationRepository.save(station);
        return "Station updated successfully";
    }

    public String deleteStation(UUID id) {
        Optional<Station> found = stationRepository.findById(id);
        if (found.isEmpty()) {
            return "The station with that id does not exist";
        }
        stationRepository.deleteById(id);
        return "Station deleted successfully";
    }
}