package com.auca.cleanflow.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auca.cleanflow.model.Attendant;
import com.auca.cleanflow.service.AttendantService;

@RestController
@RequestMapping("/api/attendants")
public class AttendantController {

    @Autowired
    private AttendantService attendantService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Attendant attendant) {
        String message = attendantService.saveAttendant(attendant);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Attendant>> getAll() {
        List<Attendant> list = attendantService.getAllAttendants();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/by-service-type")
    public ResponseEntity<List<Attendant>> getByServiceType(@RequestParam String name) {
        List<Attendant> list = attendantService.getAttendantsByServiceType(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Attendant> found = attendantService.getAttendantById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The attendant with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Attendant attendant) {
        String message = attendantService.updateAttendant(id, attendant);
        if (message.equals("Attendant updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{attendantId}/service-types/{serviceTypeId}")
    public ResponseEntity<String> addServiceType(@PathVariable UUID attendantId, @PathVariable UUID serviceTypeId) {
        String message = attendantService.addServiceType(attendantId, serviceTypeId);
        if (message.equals("Service type added to the attendant")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (message.equals("The attendant already has that service type")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{attendantId}/station")
    public ResponseEntity<String> changeStation(@PathVariable UUID attendantId, @RequestParam int stationNumber) {
        String message = attendantService.changeStation(attendantId, stationNumber);
        if (message.equals("Station changed successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (message.equals("That station is taken by another attendant")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = attendantService.deleteAttendant(id);
        if (message.equals("Attendant deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}