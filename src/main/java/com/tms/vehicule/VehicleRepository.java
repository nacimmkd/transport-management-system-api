package com.tms.vehicule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    Optional<Vehicle> findByIsActiveTrueAndId(UUID id);
    Optional<Vehicle> findByPlateNumber(String plateNumber);
    List<Vehicle> findByIsActiveTrue();
    List<Vehicle> findByIsActiveTrueAndCapacityKgGreaterThan(BigDecimal capacityKg);
}
