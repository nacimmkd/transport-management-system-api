package com.tms.employees;

import com.tms.client.Client;
import jakarta.persistence.criteria.Predicate;
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

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
