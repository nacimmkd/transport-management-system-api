package com.tms.user;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tms.company.Company;
import com.tms.driver.Driver;
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
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String phone;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "role", columnDefinition = "user_role")
    private UserRole role;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "user")
    @JsonManagedReference
    private Driver driverProfile;

    @ManyToOne @JoinColumn(name = "company_id")
    private Company company;


    // METHODS
    public void activateUser(){
        this.isActive = true;
        this.deletedAt = null;
    }

}
