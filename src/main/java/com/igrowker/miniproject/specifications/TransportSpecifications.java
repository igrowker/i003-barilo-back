package com.igrowker.miniproject.specifications;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.igrowker.miniproject.dtos.filters.TransportFilterDto;
import com.igrowker.miniproject.models.Destination;
import com.igrowker.miniproject.models.Transport;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class TransportSpecifications {
    private TransportSpecifications() {}

    public static Specification<Transport> withFilter(TransportFilterDto filter) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                predicates.add(builder.equal(root.get("name"), filter.getName()));
            }

            if (filter.getPrice() != null && filter.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                predicates.add(builder.equal(root.get("price"), filter.getPrice()));
            }

            if (filter.getTransportCategory() != null) {
                predicates.add(builder.equal(root.get("transportCategory"), filter.getTransportCategory()));
            }

            Join<Transport, Destination> destinationJoin = root.join("destination");

            if (filter.getDestinationId() != null) {
                predicates.add(builder.equal(destinationJoin.get("id"), filter.getDestinationId()));
            }

            if (filter.getDestinationName() != null && !filter.getDestinationName().isEmpty()) {
                predicates.add(builder.like(builder.lower(destinationJoin.get("name")),
                        "%" + filter.getDestinationName().toLowerCase() + "%"));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
