package com.salesianostriana.dam.calesapp.specification.parada;

import com.salesianostriana.dam.calesapp.model.Parada;
import com.salesianostriana.dam.calesapp.specification.SearchCriteria;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ParadaSpecification {

    public static Specification<Parada> withCriteria(List<SearchCriteria> criteriaList) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (SearchCriteria criteria : criteriaList) {
                switch (criteria.getOperation()) {
                    case ":" -> predicates.add(cb.like(cb.lower(root.get(criteria.getKey())),
                            "%" + criteria.getValue().toString().toLowerCase() + "%"));
                    case ">" ->
                        predicates.add(cb.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
                    case "<" ->
                        predicates.add(cb.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
