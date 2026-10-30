package com.auca.cleanflow.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_note")
public class DeliveryNote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String garmentType;

    private String remarks;

    private int quantity;

    private LocalDate deliveredDate;

    @ManyToOne
    @JoinColumn(name = "laundry_order_id")
    private LaundryOrder laundryOrder;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGarmentType() {
        return garmentType;
    }

    public void setGarmentType(String garmentType) {
        this.garmentType = garmentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDate deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public LaundryOrder getLaundryOrder() {
        return laundryOrder;
    }

    public void setLaundryOrder(LaundryOrder laundryOrder) {
        this.laundryOrder = laundryOrder;
    }
}