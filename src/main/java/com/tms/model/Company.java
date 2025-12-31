package com.tms.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

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
@Table(name = "companies")
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(updatable = false) @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(length = 9, unique = true, columnDefinition = "char(9)") @JdbcTypeCode(java.sql.Types.CHAR)
    private String siren;
    private String address;
    private String email;
    private String phone;

    @Builder.Default
    private boolean isActive = true;

    @Column(name = "deleted_at") @Builder.Default
    private LocalDateTime deletedAt = null;

}
