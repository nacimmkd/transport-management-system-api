package com.tms.model;

import com.tms.model.enums.DriverLicenseType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "drivers")
public class Driver {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "license_number")
    private String licenseNumber;

    @Enumerated(EnumType.STRING) @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "license_category")
    private DriverLicenseType licenseCategory;

    @Column(name = "license_expiry_date")
    private LocalDateTime licenseExpiryDate;

    @OneToOne @JoinColumn(name = "user_id")
    private User user;
}
