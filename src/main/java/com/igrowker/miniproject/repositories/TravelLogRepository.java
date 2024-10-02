package com.igrowker.miniproject.repositories;

import com.igrowker.miniproject.models.TravelLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelLogRepository extends JpaRepository<TravelLog, Long> {
    Page<TravelLog> findByTravelId(Long travelId, Pageable pageable);
}