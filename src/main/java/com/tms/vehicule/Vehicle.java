package com.tms.vehicule;

import com.tms.company.Company;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    private String model;
    private String brand;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "capacity_kg")
    private BigDecimal capacityKg;

    @Enumerated(EnumType.STRING) @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type")
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING) @JdbcTypeCode(SqlTypes.NAMED_ENUM) @Column(name = "status")
    @Builder.Default
    private VehicleStatus vehicleStatus = VehicleStatus.AVAILABLE;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne @JoinColumn(name = "company_id")
    private Company company;

}
