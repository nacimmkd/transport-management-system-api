package com.tms.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.isActive = true " +
            "AND u.company.id = :companyId AND u.role != com.tms.user.UserRole.ROLE_ADMIN")
    Optional<User> findActiveUserById(@Param("id") UUID id, @Param("companyId") UUID companyId);

    @Query("SELECT u FROM User u WHERE u.isActive = true AND u.company.id = :companyId " +
            "AND u.role != com.tms.user.UserRole.ROLE_ADMIN")
    List<User> findAllActiveUsers(@Param("companyId") UUID companyId);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.company.id = :companyId")
    Optional<User> findByEmail(@Param("email") String email, @Param("companyId") UUID companyId);

    @Query("SELECT u FROM User u WHERE u.isActive = true AND u.company.id = :companyId AND u.role = :role")
    List<User> findAllActiveUsersByRole(@Param("role") UserRole role, @Param("companyId") UUID companyId);
}
