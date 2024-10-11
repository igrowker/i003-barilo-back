package com.igrowker.miniproject.repositories;

import com.igrowker.miniproject.models.Destination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    Optional<Destination> findByName(String name);
    Page<Destination> findAllByCity(String city, Pageable pageable);
}
