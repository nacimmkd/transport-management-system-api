package com.tms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tms.model.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "pickup_address")
    private String pickupAddress;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "requested_delivery_time")
    private LocalDateTime requestedDeliveryTime;

    @Column(name = "planned_start_time")
    private LocalDateTime plannedStartTime;

    private BigDecimal price;

    @Enumerated(EnumType.STRING) @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Builder.Default
    private DeliveryStatus status = DeliveryStatus.PENDING;

    @Column(name = "delivery_code")
    private String deliveryCode;

    @Column(name = "delivered_at") @Builder.Default
    private LocalDateTime deliveredAt = null;

    @ManyToOne @JoinColumn(name = "client_id")
    @JsonManagedReference
    private Client client;

    @ManyToOne @JoinColumn(name = "vehicle_id")
    @JsonManagedReference
    private Vehicle vehicle;

    @ManyToOne @JoinColumn(name = "driver_id")
    @JsonManagedReference
    private Driver driver;

    @ManyToOne @JoinColumn(name = "company_id")
    @JsonManagedReference
    private Company company;

    @OneToMany(mappedBy = "delivery", cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH})
    @Builder.Default
    @JsonManagedReference
    private List<DeliveryHistory> deliveryHistory = new ArrayList<>();


    // METHODS
    public void updateStatus(DeliveryStatus newStatus) {
        if(this.status == newStatus) return;
        this.status = newStatus;
        saveInHistory(newStatus);
    }

    public void saveInHistory(DeliveryStatus status) {
        var history = DeliveryHistory.builder()
                .status(status)
                .delivery(this)
                .updatedAt(LocalDateTime.now())
                .build();
        deliveryHistory.add(history);
    }

}