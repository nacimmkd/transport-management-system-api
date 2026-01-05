package com.tms.vehicule;

import com.tms.delivery.Delivery;
import com.tms.delivery.DeliveryStatus;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VehicleSpecifications {

    // On change VehicleDto en Vehicle ici
    public static Specification<Vehicle> withCriteria(VehicleSearchCriteria criteria, UUID companyId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isFalse(root.get("isDeleted")));
            predicates.add(cb.equal(root.get("company").get("id"), companyId));

            if (criteria.status() != null) {
                predicates.add(cb.equal(root.get("vehicleStatus"), criteria.status()));
            }

            if (criteria.type() != null) {
                predicates.add(cb.equal(root.get("vehicleType"), criteria.type()));
            }

            if (criteria.plateNumber() != null && !criteria.plateNumber().isEmpty()) {
                predicates.add(cb.like(root.get("plateNumber"), "%" + criteria.plateNumber() + "%"));
            }

            if(criteria.minCapacityKg() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("capacityKg"), criteria.minCapacityKg()));
            }

            if (criteria.availableAt() != null) {
                Subquery<UUID> subquery = query.subquery(UUID.class);
                Root<Delivery> delivery = subquery.from(Delivery.class);

                subquery.select(delivery.get("id"));

                Predicate vehicleMatch = cb.equal(delivery.get("vehicle"), root);
                Predicate statusIn = delivery.get("status").in(DeliveryStatus.PENDING, DeliveryStatus.ASSIGNED, DeliveryStatus.IN_TRANSIT, DeliveryStatus.ARRIVED);

                Predicate timeOverlap = cb.between(
                        cb.literal(criteria.availableAt()),
                        delivery.get("plannedStartTime"),
                        delivery.get("requestedDeliveryTime")
                );

                subquery.where(cb.and(vehicleMatch, statusIn, timeOverlap));
                predicates.add(cb.not(cb.exists(subquery)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
