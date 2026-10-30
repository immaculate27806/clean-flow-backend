package com.auca.cleanflow.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.cleanflow.model.OrderStatus;
import com.auca.cleanflow.repository.AttendantRepository;
import com.auca.cleanflow.repository.CustomerRepository;
import com.auca.cleanflow.repository.DeliveryNoteRepository;
import com.auca.cleanflow.repository.LaundryOrderRepository;
import com.auca.cleanflow.repository.StationRepository;

@Service
public class DashboardService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AttendantRepository attendantRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LaundryOrderRepository laundryOrderRepository;

    @Autowired
    private DeliveryNoteRepository deliveryNoteRepository;

    public Map<String, Long> getSummary() {
        // LinkedHashMap keeps the keys in the order we add them
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("customers", customerRepository.count());
        summary.put("attendants", attendantRepository.count());
        summary.put("freeStations", (long) stationRepository.findFreeStations().size());
        summary.put("ordersToday", laundryOrderRepository.countByOrderDate(LocalDate.now()));
        summary.put("pending", laundryOrderRepository.countByStatus(OrderStatus.PENDING));
        summary.put("deliveryNotes", deliveryNoteRepository.count());
        return summary;
    }
}