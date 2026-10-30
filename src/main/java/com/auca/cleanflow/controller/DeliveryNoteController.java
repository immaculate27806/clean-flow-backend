package com.auca.cleanflow.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auca.cleanflow.model.DeliveryNote;
import com.auca.cleanflow.service.DeliveryNoteService;

@RestController
@RequestMapping("/api/delivery-notes")
public class DeliveryNoteController {

    @Autowired
    private DeliveryNoteService deliveryNoteService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody DeliveryNote deliveryNote, @RequestParam UUID laundryOrderId) {
        String message = deliveryNoteService.saveDeliveryNote(deliveryNote, laundryOrderId);
        if (message.equals("Delivery note saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else if (message.equals("The laundry order with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeliveryNote>> getAll() {
        List<DeliveryNote> list = deliveryNoteService.getAllDeliveryNotes();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/customer/{phone}")
    public ResponseEntity<List<DeliveryNote>> getOfCustomer(@PathVariable String phone) {
        List<DeliveryNote> list = deliveryNoteService.getNotesOfCustomer(phone);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = deliveryNoteService.deleteDeliveryNote(id);
        if (message.equals("Delivery note deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}