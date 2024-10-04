package com.igrowker.miniproject.repositories;

import com.igrowker.miniproject.models.Meal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    Page<Meal> findAllByDestinationId(Long destinationId, Pageable pageable);
}
