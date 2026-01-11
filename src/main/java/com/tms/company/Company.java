package com.tms.company;

import com.tms.client.Client;
import com.tms.delivery.Delivery;
import com.tms.employees.Employee;
import com.tms.vehicule.Vehicle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLRestriction;

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
@SQLRestriction("deleted = false")
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(length = 9, columnDefinition = "char(9)") @JdbcTypeCode(java.sql.Types.CHAR)
    private String siren;
    private String address;
    private String email;
    private String phone;

    @Column(name = "deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at") @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "company", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @Builder.Default
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @Builder.Default
    private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @Builder.Default
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @Builder.Default
    private List<Delivery> deliveries = new ArrayList<>();

    public void deleteCompany() {
        deleteAllVehicles();
        deleteAllClients();
        deleteAllEmployees();
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    private void deleteAllVehicles(){
        vehicles.forEach(vehicle -> {
            vehicle.setDeleted(true);
            vehicle.setDeletedAt(LocalDateTime.now());
        });
    }

    private void deleteAllClients(){
        clients.forEach(client -> {
            client.setDeleted(true);
            client.setDeletedAt(LocalDateTime.now());
        });
    }


    private void deleteAllEmployees(){
        employees.forEach(employee -> {
            employee.setDeleted(true);
            employee.setDeletedAt(LocalDateTime.now());
        });
    }

}
