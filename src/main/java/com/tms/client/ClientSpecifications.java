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

            predicates.add(cb.isFalse(root.get("isDeleted")));
            predicates.add(cb.equal(root.get("company").get("id"), companyId));

            if(criteria.name() != null) predicates.add(cb.equal(root.get("name"), criteria.name()));
            if(criteria.email() != null) predicates.add(cb.equal(root.get("email"), criteria.email()));
            if(criteria.phone() != null) predicates.add(cb.equal(root.get("phone"), criteria.phone()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
