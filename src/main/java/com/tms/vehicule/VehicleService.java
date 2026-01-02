package com.tms.vehicule;

import com.tms.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CompanyRepository companyRepository;


    public VehicleDto findById(UUID id) {
        var vehicle = vehicleRepository.findById(id).orElseThrow(VehicleNotFoundException::new);
        return VehicleMapper.toDto(vehicle);
    }

    public List<VehicleDto> findAll() {
        return vehicleRepository.findByIsActiveTrue().stream()
                .map(VehicleMapper::toDto)
                .toList();
    }

    public List<VehicleDto> findByCapacityKgGreaterThan(BigDecimal capacityKg) {
        return vehicleRepository.findByIsActiveTrueAndCapacityKgGreaterThan(capacityKg)
                .stream()
                .map(VehicleMapper::toDto)
                .toList();
    }


    public VehicleDto registerVehicle(VehicleRequest vehicleRequest) {

        // IMPORTANT: a modifier par la suite
        var company = companyRepository.findById(UUID.fromString("aed2f7aa-5eca-4df1-8881-87a5754350c2")).orElse(null);

        var vehicle = vehicleRepository.findByPlateNumber(vehicleRequest.plateNumber()).orElse(null);
        if (vehicle == null) {
            var newVehicle = VehicleMapper.toEntity(vehicleRequest, company);
            vehicleRepository.save(newVehicle);
            return VehicleMapper.toDto(newVehicle);
        }
        else {
            return updateVehicle(vehicle.getId(), vehicleRequest);
        }
    }


    public VehicleDto updateVehicle(UUID id, VehicleRequest vehicleRequest) {
        var vehicle = vehicleRepository.findByIsActiveTrueAndId(id).orElseThrow(VehicleNotFoundException::new);
        vehicle.setBrand(vehicleRequest.brand());
        vehicle.setModel(vehicleRequest.model());
        vehicle.setPlateNumber(vehicleRequest.plateNumber());
        vehicle.setVehicleType(vehicleRequest.type());
        vehicle.setCapacityKg(vehicleRequest.capacityKg());
        return VehicleMapper.toDto(vehicleRepository.save(vehicle));
    }


    public void deleteVehicle(UUID id) {
        var vehicle = vehicleRepository.findByIsActiveTrueAndId(id).orElseThrow(VehicleNotFoundException::new);
        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
    }
}
