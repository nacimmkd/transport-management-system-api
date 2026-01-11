package com.tms.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {

    @Query("SELECT u FROM Employee u LEFT JOIN FETCH u.driverProfile WHERE u.id = :id AND u.isDeleted = false AND u.company.id = :companyId AND u.role != com.tms.employees.EmployeeRole.ROLE_ADMIN")
    Optional<Employee> findActiveUserById(@Param("id") UUID id, @Param("companyId") UUID companyId);

    @Query("SELECT u FROM Employee u LEFT JOIN FETCH u.driverProfile WHERE u.isDeleted = false AND u.company.id = :companyId AND u.role != com.tms.employees.EmployeeRole.ROLE_ADMIN")
    List<Employee> findAllActiveUsers(@Param("companyId") UUID companyId);

    @Query("SELECT u FROM Employee u LEFT JOIN FETCH u.driverProfile WHERE u.email = :email AND u.company.id = :companyId AND u.isDeleted = false")
    Optional<Employee> findActiveByEmail(@Param("email") String email, @Param("companyId") UUID companyId);

    @Query("SELECT u FROM Employee u LEFT JOIN FETCH u.driverProfile WHERE u.isDeleted = false AND u.company.id = :companyId AND u.role = :role")
    List<Employee> findAllActiveUsersByRole(@Param("role") EmployeeRole role, @Param("companyId") UUID companyId);

    @Query("SELECT COUNT(u) > 0 FROM Employee u WHERE u.email = :email AND u.company.id = :companyId AND u.isDeleted = false")
    boolean existsByEmailAndCompanyId(String email, UUID companyId);
}
