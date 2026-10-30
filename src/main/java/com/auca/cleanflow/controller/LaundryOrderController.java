package com.auca.cleanflow.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auca.cleanflow.model.LaundryOrder;
import com.auca.cleanflow.model.OrderStatus;
import com.auca.cleanflow.service.LaundryOrderService;

@RestController
@RequestMapping("/api/laundry-orders")
public class LaundryOrderController {

    @Autowired
    private LaundryOrderService laundryOrderService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody LaundryOrder laundryOrder) {
        String message = laundryOrderService.saveLaundryOrder(laundryOrder);
        if (message.equals("Laundry order saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else if (message.equals("Laundry order date cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<LaundryOrder>> getAll() {
        List<LaundryOrder> list = laundryOrderService.getAllLaundryOrders();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<LaundryOrder>> getByStatus(@RequestParam OrderStatus status) {
        List<LaundryOrder> list = laundryOrderService.getLaundryOrdersByStatus(status);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<List<LaundryOrder>> getBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        List<LaundryOrder> list = laundryOrderService.getLaundryOrdersBetween(startDate, endDate);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getStatsByStatus() {
        List<Object[]> stats = laundryOrderService.getOrderStatsByStatus();
        return new ResponseEntity<>(stats, HttpStatus.OK);
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam OrderStatus status) {
        String message = laundryOrderService.updateStatus(id, status);
        if (message.equals("Laundry order status updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (message.startsWith("Cannot change status")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID attendantId, @RequestParam String date) {
        LocalDate day = LocalDate.parse(date);
        String message = laundryOrderService.cancelDay(attendantId, day);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = laundryOrderService.deleteLaundryOrder(id);
        if (message.equals("Laundry order deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (message.equals("That laundry order has delivery notes, delete them first")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}