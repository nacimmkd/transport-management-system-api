package com.tms.services.deliveryService;

import com.tms.model.Delivery;
import com.tms.model.Driver;
import com.tms.repository.DeliveryRepository;
import com.tms.services.deliveryService.exceptions.DeliveryNotFoundException;
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

    public List<Delivery> getDeliveriesByDriver(Driver driver) {
        return deliveryRepository.getDeliveriesByDriver(driver);
    }


}
