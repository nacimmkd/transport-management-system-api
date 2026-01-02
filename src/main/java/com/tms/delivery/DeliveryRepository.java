package com.tms.delivery;

import com.tms.driver.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

    @Query("SELECT d FROM Delivery d WHERE d.driver = :driver")
    List<Delivery> getDeliveriesByDriver(@Param("driver") Driver driver);
}
