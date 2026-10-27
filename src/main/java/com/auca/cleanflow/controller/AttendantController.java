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