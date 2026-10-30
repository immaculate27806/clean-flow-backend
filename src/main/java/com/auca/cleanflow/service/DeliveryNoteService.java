package com.auca.cleanflow.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.cleanflow.model.DeliveryNote;
import com.auca.cleanflow.model.LaundryOrder;
import com.auca.cleanflow.model.OrderStatus;
import com.auca.cleanflow.repository.DeliveryNoteRepository;
import com.auca.cleanflow.repository.LaundryOrderRepository;

@Service
public class DeliveryNoteService {

    @Autowired
    private DeliveryNoteRepository deliveryNoteRepository;

    @Autowired
    private LaundryOrderRepository laundryOrderRepository;

    public String saveDeliveryNote(DeliveryNote deliveryNote, UUID laundryOrderId) {
        Optional<LaundryOrder> found = laundryOrderRepository.findById(laundryOrderId);
        if (found.isEmpty()) {
            return "The laundry order with that id does not exist";
        }
        LaundryOrder laundryOrder = found.get();

        // a note can only be written once the order is delivered
        if (laundryOrder.getStatus() != OrderStatus.DELIVERED) {
            return "Delivery notes are only for DELIVERED laundry orders";
        }
        deliveryNote.setDeliveredDate(LocalDate.now());
        deliveryNote.setLaundryOrder(laundryOrder);
        deliveryNoteRepository.save(deliveryNote);
        return "Delivery note saved successfully";
    }

    public List<DeliveryNote> getAllDeliveryNotes() {
        return deliveryNoteRepository.findAll();
    }

    public List<DeliveryNote> getNotesOfCustomer(String phone) {
        return deliveryNoteRepository.findNotesOfCustomer(phone);
    }

    public String deleteDeliveryNote(UUID id) {
        Optional<DeliveryNote> found = deliveryNoteRepository.findById(id);
        if (found.isEmpty()) {
            return "The delivery note with that id does not exist";
        }
        deliveryNoteRepository.deleteById(id);
        return "Delivery note deleted successfully";
    }
}