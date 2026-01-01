package com.tms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tms.model.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "deliveries_history")
public class DeliveryHistory {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;

    @Enumerated(EnumType.STRING) @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private DeliveryStatus status;

    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne @JoinColumn(name = "delivery_id")
    @JsonBackReference
    private Delivery delivery;

}