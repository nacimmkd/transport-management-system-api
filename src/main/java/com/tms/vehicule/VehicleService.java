package com.tms.vehicule;

import com.tms.company.CompanyNotFoundException;
import com.tms.company.CompanyRepository;
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
        var vehicle = vehicleRepository.findActiveVehicleById(id,companyId).orElseThrow(VehicleNotFoundException::new);
        return VehicleMapper.toDto(vehicle);
    }

    public List<VehicleDto> findAll() {
        return vehicleRepository.findAllActiveVehicles(companyId).stream()
                .map(VehicleMapper::toDto)
                .toList();
    }

    public List<VehicleDto> searchVehicle(VehicleSearchCriteria criteria) {
         Specification<Vehicle> spec = VehicleSpecifications.withCriteria(criteria, companyId);
         return vehicleRepository.findAll(spec).stream()
                 .map(VehicleMapper::toDto)
                 .toList();
    }


    public VehicleDto registerVehicle(VehicleRequest vehicleRequest) {
        var company = companyRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
        return vehicleRepository.findByPlateNumber(vehicleRequest.plateNumber(),companyId)
                .map(existing -> updateVehicle(existing.getId(), vehicleRequest))
                .orElseGet(() -> {
                    var newVehicle = VehicleMapper.toEntity(vehicleRequest,company);
                    return VehicleMapper.toDto(vehicleRepository.save(newVehicle));
                });

    }

    public VehicleDto updateVehicle(UUID id, VehicleRequest vehicleRequest) {
        var vehicle = vehicleRepository.findById(id)
                .filter(existing -> existing.getCompany().getId().equals(companyId))
                .orElseThrow(VehicleNotFoundException::new);

        // Verify if another vehicle has the same plate number
        vehicleRepository.findByPlateNumber(vehicleRequest.plateNumber(), companyId)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new VehicleExistsException();
                    }
                });

        // update
        vehicle.setBrand(vehicleRequest.brand());
        vehicle.setModel(vehicleRequest.model());
        vehicle.setPlateNumber(vehicleRequest.plateNumber());
        vehicle.setVehicleType(vehicleRequest.type());
        vehicle.setCapacityKg(vehicleRequest.capacityKg());

        vehicle.setDeletedAt(null);
        vehicle.setActive(true);
        return VehicleMapper.toDto(vehicleRepository.save(vehicle));
    }

    public VehicleDto updateVehicleStatus(UUID id, UpdateStatusRequest request) {
        var  vehicle = vehicleRepository.findActiveVehicleById(id,companyId).orElseThrow(VehicleNotFoundException::new);
        vehicle.setVehicleStatus(request.status());
        return VehicleMapper.toDto(vehicleRepository.save(vehicle));
    }

    public void deleteVehicle(UUID id) {
        var vehicle = vehicleRepository.findActiveVehicleById(id,companyId).orElseThrow(VehicleNotFoundException::new);
        vehicle.setActive(false);
        vehicle.setDeletedAt(LocalDateTime.now());
        vehicleRepository.save(vehicle);
    }
}
