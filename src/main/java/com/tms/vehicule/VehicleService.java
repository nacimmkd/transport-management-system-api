package com.tms.vehicule;

import com.tms.company.CompanyNotFoundException;
import com.tms.company.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CompanyRepository companyRepository;
    private final UUID companyId = UUID.fromString("aed2f7aa-5eca-4df1-8881-87a5754350c2");


    public VehicleDto findById(UUID id) {
        var vehicle = vehicleRepository.findVehicleById(id,companyId).orElseThrow(VehicleNotFoundException::new);
        return VehicleMapper.toDto(vehicle);
    }

    public List<VehicleDto> findAll() {
        return vehicleRepository.findAllVehicles(companyId).stream()
                .map(VehicleMapper::toDto)
                .toList();
    }

    public List<VehicleDto> searchVehicle(VehicleSearchCriteria criteria) {
         Specification<Vehicle> spec = VehicleSpecifications.withCriteria(criteria, companyId);
         return vehicleRepository.findAll(spec).stream()
                 .map(VehicleMapper::toDto)
                 .toList();
    }

    @Transactional
    public VehicleDto registerVehicle(VehicleRequest vehicleRequest) {
        var company = companyRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);

        var existingVehicle = vehicleRepository.findByPlateNumber(vehicleRequest.plateNumber().toUpperCase(), companyId);
        if(existingVehicle.isPresent()) throw new VehicleExistsException();

        var newVehicle = VehicleMapper.toEntity(vehicleRequest,company);
        return VehicleMapper.toDto(vehicleRepository.save(newVehicle));
    }

    @Transactional
    public VehicleDto updateVehicle(UUID id, VehicleRequest vehicleRequest) {
        // findActiveClientById to not modify deleted vehicle
        var vehicle = vehicleRepository.findVehicleById(id, companyId)
                .orElseThrow(VehicleNotFoundException::new);

        String normalizedPlate = vehicleRequest.plateNumber().toUpperCase();

        // Verify email only on vehicles not deleted
        vehicleRepository.findByPlateNumber(normalizedPlate, companyId)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new VehicleExistsException();
                    }
                });

        // update
        vehicle.setBrand(vehicleRequest.brand());
        vehicle.setModel(vehicleRequest.model());
        vehicle.setPlateNumber(normalizedPlate);
        vehicle.setVehicleType(vehicleRequest.type());
        vehicle.setCapacityKg(vehicleRequest.capacityKg());

        return VehicleMapper.toDto(vehicleRepository.save(vehicle));
    }

    @Transactional
    public VehicleDto updateVehicleStatus(UUID id, UpdateStatusRequest request) {
        var  vehicle = vehicleRepository.findVehicleById(id,companyId).orElseThrow(VehicleNotFoundException::new);
        vehicle.setVehicleStatus(request.vehicleStatus());
        return VehicleMapper.toDto(vehicleRepository.save(vehicle));
    }

    @Transactional
    public void deleteVehicle(UUID id) {
        var vehicle = vehicleRepository.findVehicleById(id,companyId).orElseThrow(VehicleNotFoundException::new);
        vehicle.setDeleted(true);
        vehicle.setDeletedAt(LocalDateTime.now());
        vehicleRepository.save(vehicle);
    }
}
