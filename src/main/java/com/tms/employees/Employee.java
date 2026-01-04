package com.tms.employees;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tms.company.Company;
import com.tms.employees.driverProfile.DriverProfile;
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

    @OneToOne(mappedBy = "employee", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private DriverProfile driverProfile;

    @ManyToOne @JoinColumn(name = "company_id")
    private Company company;

    public void addDriverProfile(DriverProfile profile) {
        if (profile == null) return;
        this.driverProfile = profile;
        profile.setEmployee(this);
    }

}
