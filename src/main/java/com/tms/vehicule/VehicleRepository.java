package com.tms.vehicule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID>, JpaSpecificationExecutor<Vehicle> {

    @Query("SELECT v FROM Vehicle v WHERE v.id=:id AND v.isDeleted=false AND v.company.id=:companyId")
    Optional<Vehicle> findVehicleById(@Param("id") UUID uuid, @Param("companyId") UUID companyId);

    @Query("SELECT v FROM Vehicle v WHERE v.isDeleted=false AND v.company.id = :companyId")
    List<Vehicle> findAllVehicles(@Param("companyId") UUID companyId);

    @Query("SELECT v FROM Vehicle v WHERE v.plateNumber=:plateNumber AND v.company.id=:companyId AND v.isDeleted=false")
    Optional<Vehicle> findByPlateNumber(@Param("plateNumber") String plateNumber, @Param("companyId") UUID companyId);

}
