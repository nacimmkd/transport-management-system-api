package com.tms.employees;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tms.company.Company;
import com.tms.employees.driver.DriverProfile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String phone;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "role", columnDefinition = "employeerole")
    private EmployeeRole role;

    @Column(name = "deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "employee", cascade = {CascadeType.PERSIST, CascadeType.MERGE,  CascadeType.REFRESH})
    @JsonManagedReference
    private DriverProfile driverProfile;

    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "company_id")
    private Company company;

    public void addDriverProfile(DriverProfile profile) {
        if (profile == null) return;
        profile.setEmployee(this);
        this.driverProfile = profile;
    }

    public void deleteDriverProfile(DriverProfile profile) {
        if (profile == null) return;
        driverProfile.setLicenseNumber(profile.getLicenseNumber() + "_DELETED_" +  UUID.randomUUID());
    }

    public void updateDriverProfile(DriverProfile profiler) {
        if (profiler == null) return;
        this.driverProfile.setLicenseNumber(profiler.getLicenseNumber().toUpperCase());
        this.driverProfile.setLicenseExpiryDate(profiler.getLicenseExpiryDate());
        this.driverProfile.setLicenseCategory(profiler.getLicenseCategory());
    }

}
