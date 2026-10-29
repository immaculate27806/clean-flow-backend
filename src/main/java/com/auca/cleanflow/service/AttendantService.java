package com.auca.cleanflow.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.cleanflow.model.Attendant;
import com.auca.cleanflow.model.ServiceType;
import com.auca.cleanflow.model.Station;
import com.auca.cleanflow.repository.AttendantRepository;
import com.auca.cleanflow.repository.ServiceTypeRepository;
import com.auca.cleanflow.repository.StationRepository;

@Service
public class AttendantService {

    @Autowired
    private AttendantRepository attendantRepository;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private StationRepository stationRepository;

    public String saveAttendant(Attendant attendant) {
        attendantRepository.save(attendant);
        return "Attendant saved successfully";
    }

    public List<Attendant> getAllAttendants() {
        return attendantRepository.findAll();
    }

    public Optional<Attendant> getAttendantById(UUID id) {
        return attendantRepository.findById(id);
    }

    public String updateAttendant(UUID id, Attendant newData) {
        Optional<Attendant> found = attendantRepository.findById(id);
        if (found.isEmpty()) {
            return "The attendant with that id does not exist";
        }
        Attendant attendant = found.get();
        attendant.setFirstName(newData.getFirstName());
        attendant.setLastName(newData.getLastName());
        attendant.setEmail(newData.getEmail());
        attendant.setStation(newData.getStation());
        if (newData.getServiceTypes() != null) {
            attendant.setServiceTypes(newData.getServiceTypes());
        }
        attendantRepository.save(attendant);
        return "Attendant updated successfully";
    }

    public String deleteAttendant(UUID id) {
        Optional<Attendant> found = attendantRepository.findById(id);
        if (found.isEmpty()) {
            return "The attendant with that id does not exist";
        }
        attendantRepository.deleteById(id);
        return "Attendant deleted successfully";
    }

    public List<Attendant> getAttendantsByServiceType(String name) {
        return attendantRepository.findByServiceTypeName(name);
    }

    public String addServiceType(UUID attendantId, UUID serviceTypeId) {
        Optional<Attendant> foundAttendant = attendantRepository.findById(attendantId);
        if (foundAttendant.isEmpty()) {
            return "The attendant with that id does not exist";
        }
        Optional<ServiceType> foundServiceType = serviceTypeRepository.findById(serviceTypeId);
        if (foundServiceType.isEmpty()) {
            return "That service type does not exist";
        }
        Attendant attendant = foundAttendant.get();
        ServiceType serviceType = foundServiceType.get();

        if (attendant.getServiceTypes().contains(serviceType)) {
            return "The attendant already has that service type";
        }
        attendant.getServiceTypes().add(serviceType);
        attendantRepository.save(attendant);
        return "Service type added to the attendant";
    }

    public String changeStation(UUID attendantId, int stationNumber) {
        Optional<Attendant> foundAttendant = attendantRepository.findById(attendantId);
        if (foundAttendant.isEmpty()) {
            return "The attendant with that id does not exist";
        }
        Optional<Station> foundStation = stationRepository.findByStationNumber(stationNumber);
        if (foundStation.isEmpty()) {
            return "The station with that stationNumber does not exist";
        }
        Attendant attendant = foundAttendant.get();
        Station station = foundStation.get();

        if (attendantRepository.existsByStation(station)) {
            return "That station is taken by another attendant";
        }
        attendant.setStation(station);
        attendantRepository.save(attendant);
        return "Station changed successfully";
    }
}