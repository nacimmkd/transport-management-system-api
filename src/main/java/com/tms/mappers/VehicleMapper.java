package com.tms.mappers;

import com.tms.dtos.VehicleDto;
import com.tms.dtos.VehicleRequest;
import com.tms.model.Company;
import com.tms.model.Vehicle;
import com.tms.model.enums.VehicleStatus;

public class VehicleMapper {

    public static VehicleDto toDto(Vehicle vehicle) {
        return VehicleDto.builder()
                .id(vehicle.getId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .type(vehicle.getVehicleType())
                .plateNumber(vehicle.getPlateNumber())
                .status(vehicle.getVehicleStatus())
                .capacityKg(vehicle.getCapacityKg())
                .build();
    }

    public static Vehicle toEntity(VehicleRequest request, Company company) {
        return Vehicle.builder()
                .brand(request.brand())
                .model(request.model())
                .vehicleType(request.type())
                .plateNumber(request.plateNumber())
                .capacityKg(request.capacityKg())
                .vehicleStatus(VehicleStatus.AVAILABLE)
                .company(company)
                .build();
    }
}
