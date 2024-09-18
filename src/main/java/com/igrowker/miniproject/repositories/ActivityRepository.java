package com.igrowker.miniproject.repositories;

import com.igrowker.miniproject.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByDestinationId(Long destinationId);
}
