package com.igrowker.miniproject.specifications;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.igrowker.miniproject.dtos.filters.AccommodationFilterDto;
import com.igrowker.miniproject.models.Accommodation;
import com.igrowker.miniproject.models.Destination;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class AccommodationSpecifications {

    private AccommodationSpecifications() {}

    public static Specification<Accommodation> withFilter(AccommodationFilterDto filter) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null) {
                predicates.add(builder.like(root.get("name"), "%" + filter.getName() + "%"));
            }

            if (filter.getPrice() != null && filter.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                predicates.add(builder.equal(root.get("price"), filter.getPrice()));
            }

            if (filter.getType() != null) {
                predicates.add(builder.equal(root.get("type"), filter.getType()));
            }

            Join<Accommodation, Destination> destinationJoin = root.join("destination");

            if (filter.getDestinationId() != null) {
                predicates.add(builder.equal(destinationJoin.get("id"), filter.getDestinationId()));
            }
            if (filter.getDestinationName() != null) {
                predicates.add(builder.like(builder.lower(destinationJoin.get("name")),
                        "%" + filter.getDestinationName().toLowerCase() + "%"));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
