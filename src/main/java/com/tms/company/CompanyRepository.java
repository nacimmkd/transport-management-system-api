package com.tms.company;

import com.tms.employees.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByIdAndIsDeletedFalse(UUID id);
    boolean existsByEmailAndIsDeletedFalse(String email);
}
