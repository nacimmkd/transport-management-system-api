package com.tms.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID>, JpaSpecificationExecutor<Client> {

    @Query("SELECT c FROM Client c WHERE c.id=:id AND c.isActive=true AND c.company.id=:companyId")
    Optional<Client> findActiveClientById(@Param("id") UUID uuid, @Param("companyId") UUID companyId);

    @Query("SELECT c FROM Client c WHERE c.isActive = true AND c.company.id = :companyId")
    List<Client> findAllActiveClients(@Param("companyId") UUID companyId);

    @Query("SELECT c FROM Client c WHERE c.email = :email AND c.company.id=:companyId")
    Optional<Client> findByEmail(@Param("email") String email, @Param("companyId") UUID companyId);
}
