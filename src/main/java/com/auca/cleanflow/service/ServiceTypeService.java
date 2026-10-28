package com.auca.cleanflow.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auca.cleanflow.model.ServiceType;
import com.auca.cleanflow.repository.ServiceTypeRepository;

@Service
public class ServiceTypeService {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    public String saveServiceType(ServiceType serviceType) {
        serviceTypeRepository.save(serviceType);
        return "Service type saved successfully";
    }

    public List<ServiceType> getAllServiceTypes() {
        return serviceTypeRepository.findAll();
    }

    public Optional<ServiceType> getServiceTypeById(UUID id) {
        return serviceTypeRepository.findById(id);
    }

    public String updateServiceType(UUID id, ServiceType newData) {
        Optional<ServiceType> found = serviceTypeRepository.findById(id);
        if (found.isEmpty()) {
            return "The service type with that id does not exist";
        }
        ServiceType serviceType = found.get();
        serviceType.setName(newData.getName());
        serviceTypeRepository.save(serviceType);
        return "Service type updated successfully";
    }

    public String deleteServiceType(UUID id) {
        Optional<ServiceType> found = serviceTypeRepository.findById(id);
        if (found.isEmpty()) {
            return "The service type with that id does not exist";
        }
        serviceTypeRepository.deleteById(id);
        return "Service type deleted successfully";
    }

    public List<ServiceType> getUnusedServiceTypes() {
        return serviceTypeRepository.findUnusedServiceTypes();
    }
}