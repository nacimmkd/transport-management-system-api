package com.tms.employees.driver_profile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tms.employees.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "driver_profile")
public class DriverProfile {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "license_number")
    private String licenseNumber;

    @Enumerated(EnumType.STRING) @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "license_category")
    private LicenseCategory licenseCategory;

    @Column(name = "license_expiry_date")
    private LocalDate licenseExpiryDate;

    @OneToOne @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;
}
