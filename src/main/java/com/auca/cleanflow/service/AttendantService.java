package com.auca.cleanflow.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.cleanflow.model.Attendant;
import com.auca.cleanflow.repository.AttendantRepository;

@Service
public class AttendantService {

    @Autowired
    private AttendantRepository attendantRepository;

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
}