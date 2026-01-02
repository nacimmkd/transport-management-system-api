package com.tms.delivery;

import com.tms.client.Client;
import com.tms.driver.Driver;
import com.tms.company.Company;
import com.tms.vehicule.Vehicle;
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

    @Column(name = "weight_kg")
    private BigDecimal weightKg;

    @Enumerated(EnumType.STRING) @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Builder.Default
    private DeliveryStatus status = DeliveryStatus.PENDING;

    @Column(name = "delivery_code")
    private String deliveryCode;

    @Column(name = "delivered_at") @Builder.Default
    private LocalDateTime deliveredAt = null;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH})
    @Builder.Default
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