package com.auca.cleanflow.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.cleanflow.model.LaundryOrder;
import com.auca.cleanflow.model.OrderStatus;
import com.auca.cleanflow.repository.LaundryOrderRepository;

@Service
public class LaundryOrderService {

    @Autowired
    private LaundryOrderRepository laundryOrderRepository;

    public String saveLaundryOrder(LaundryOrder laundryOrder) {
        // rule 1: the date cannot be before today
        if (laundryOrder.getOrderDate().isBefore(LocalDate.now())) {
            return "Laundry order date cannot be in the past";
        }
        // rule 2: the attendant must be free on that date (cancelled orders do not count)
        boolean busy = laundryOrderRepository.existsByAttendantAndOrderDateAndStatusNot(
                laundryOrder.getAttendant(), laundryOrder.getOrderDate(), OrderStatus.CANCELLED);
        if (busy) {
            return "Attendant already has an order on that date";
        }
        // rule 3: a new order always starts as PENDING
        laundryOrder.setStatus(OrderStatus.PENDING);
        laundryOrderRepository.save(laundryOrder);
        return "Laundry order saved successfully";
    }

    public List<LaundryOrder> getAllLaundryOrders() {
        return laundryOrderRepository.findAll();
    }

    public Optional<LaundryOrder> getLaundryOrderById(UUID id) {
        return laundryOrderRepository.findById(id);
    }

    public String updateLaundryOrder(UUID id, LaundryOrder newData) {
        Optional<LaundryOrder> found = laundryOrderRepository.findById(id);
        if (found.isEmpty()) {
            return "The laundry order with that id does not exist";
        }
        LaundryOrder laundryOrder = found.get();
        laundryOrder.setOrderDate(newData.getOrderDate());
        laundryOrder.setDeliveryOption(newData.getDeliveryOption());
        laundryOrder.setItemCount(newData.getItemCount());
        laundryOrder.setCustomer(newData.getCustomer());
        laundryOrder.setAttendant(newData.getAttendant());
        laundryOrderRepository.save(laundryOrder);
        return "Laundry order updated successfully";
    }

    public String deleteLaundryOrder(UUID id) {
        Optional<LaundryOrder> found = laundryOrderRepository.findById(id);
        if (found.isEmpty()) {
            return "The laundry order with that id does not exist";
        }
        laundryOrderRepository.deleteById(id);
        return "Laundry order deleted successfully";
    }

    public List<LaundryOrder> getLaundryOrdersByStatus(OrderStatus status) {
        return laundryOrderRepository.findByStatusOrderByOrderDateAsc(status);
    }

    public List<LaundryOrder> getLaundryOrdersBetween(LocalDate start, LocalDate end) {
        return laundryOrderRepository.findByOrderDateBetweenOrderByOrderDateAsc(start, end);
    }

    public String updateStatus(UUID id, OrderStatus newStatus) {
        Optional<LaundryOrder> found = laundryOrderRepository.findById(id);
        if (found.isEmpty()) {
            return "The laundry order with that id does not exist";
        }
        LaundryOrder laundryOrder = found.get();
        OrderStatus current = laundryOrder.getStatus();

        boolean allowed = false;
        if (current == OrderStatus.PENDING) {
            if (newStatus == OrderStatus.WASHING || newStatus == OrderStatus.CANCELLED) {
                allowed = true;
            }
        } else if (current == OrderStatus.WASHING) {
            if (newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.CANCELLED) {
                allowed = true;
            }
        }
        // DELIVERED and CANCELLED are final, so allowed stays false for them

        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        laundryOrder.setStatus(newStatus);
        laundryOrderRepository.save(laundryOrder);
        return "Laundry order status updated successfully";
    }
}