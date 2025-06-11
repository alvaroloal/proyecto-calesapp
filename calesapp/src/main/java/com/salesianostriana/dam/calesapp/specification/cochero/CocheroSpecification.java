package com.salesianostriana.dam.calesapp.specification.cochero;

import com.salesianostriana.dam.calesapp.model.Cochero;
import com.salesianostriana.dam.calesapp.specification.SearchCriteria;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;

public class CocheroSpecification implements Specification<Cochero> {

    private final SearchCriteria criteria;

    public CocheroSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Cochero> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        switch (criteria.getOperation()) {
            case ":":
                return builder.like(builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toLowerCase() + "%");
            case ">":
                return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue());
            case "<":
                return builder.lessThan(root.get(criteria.getKey()), criteria.getValue());
            default:
                return null;
        }
    }
}
