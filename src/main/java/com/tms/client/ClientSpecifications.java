package com.tms.client;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientSpecifications {

    public static Specification<Client> withCriteria(ClientSearchCriteria criteria, UUID companyId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isTrue(root.get("isActive")));
            predicates.add(cb.equal(root.get("company").get("id"), companyId));

            if(criteria.getName() != null) predicates.add(cb.equal(root.get("name"), criteria.getName()));
            if(criteria.getEmail() != null) predicates.add(cb.equal(root.get("email"), criteria.getEmail()));
            if(criteria.getPhone() != null) predicates.add(cb.equal(root.get("phone"), criteria.getPhone()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
