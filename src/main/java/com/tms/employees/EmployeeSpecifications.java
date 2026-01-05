package com.tms.employees;

import com.tms.client.Client;
import com.tms.delivery.Delivery;
import com.tms.delivery.DeliveryStatus;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmployeeSpecifications {

    public static Specification<Employee> withCriteria(EmployeeSearchCriteria criteria, UUID companyId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isFalse(root.get("isDeleted")));
            predicates.add(cb.equal(root.get("company").get("id"), companyId));

            if(criteria.username() != null) predicates.add(cb.equal(root.get("name"), criteria.username()));
            if(criteria.email() != null) predicates.add(cb.equal(root.get("email"), criteria.email()));
            if(criteria.phone() != null) predicates.add(cb.equal(root.get("email"), criteria.phone()));
            if(criteria.role() != null) predicates.add(cb.equal(root.get("phone"), criteria.role()));
            if (EmployeeAllowedRoles.ROLE_DRIVER.equals(criteria.role()) && criteria.availableAt() != null) {
                Subquery<UUID> subquery = query.subquery(UUID.class);
                Root<Delivery> deliveryRoot = subquery.from(Delivery.class);

                subquery.select(deliveryRoot.get("id"));

                Predicate driverMatch = cb.equal(deliveryRoot.get("driver"), root.get("driverProfile"));
                Predicate activeStatus = deliveryRoot.get("status").in(
                        DeliveryStatus.PENDING,
                        DeliveryStatus.ASSIGNED,
                        DeliveryStatus.IN_TRANSIT,
                        DeliveryStatus.ARRIVED
                );
                Predicate timeOverlap = cb.and(
                        cb.lessThanOrEqualTo(deliveryRoot.get("plannedStartTime"), criteria.availableAt()),
                        cb.greaterThanOrEqualTo(deliveryRoot.get("requestedDeliveryTime"), criteria.availableAt())
                );
                subquery.where(cb.and(driverMatch, activeStatus, timeOverlap));
                predicates.add(cb.not(cb.exists(subquery)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
