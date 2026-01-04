package com.tms.delivery;

import com.tms.employees.driverProfile.DriverProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public List<Delivery> getDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(UUID id) {
        return deliveryRepository.findById(id).orElseThrow(DeliveryNotFoundException::new);
    }

    public List<Delivery> getDeliveriesByDriver(DriverProfile driver) {
        return deliveryRepository.getDeliveriesByDriver(driver);
    }


}
