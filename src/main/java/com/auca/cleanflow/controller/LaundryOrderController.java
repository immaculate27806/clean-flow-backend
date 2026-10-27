package com.auca.cleanflow.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auca.cleanflow.model.LaundryOrder;
import com.auca.cleanflow.service.LaundryOrderService;

@RestController
@RequestMapping("/api/laundry-orders")
public class LaundryOrderController {

    @Autowired
    private LaundryOrderService laundryOrderService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody LaundryOrder laundryOrder) {
        String message = laundryOrderService.saveLaundryOrder(laundryOrder);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LaundryOrder>> getAll() {
        List<LaundryOrder> list = laundryOrderService.getAllLaundryOrders();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<LaundryOrder> found = laundryOrderService.getLaundryOrderById(id);
        if (found.isPresent()) {
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The laundry order with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody LaundryOrder laundryOrder) {
        String message = laundryOrderService.updateLaundryOrder(id, laundryOrder);
        if (message.equals("Laundry order updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = laundryOrderService.deleteLaundryOrder(id);
        if (message.equals("Laundry order deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}