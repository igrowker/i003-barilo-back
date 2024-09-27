package com.igrowker.miniproject.repositories;

import com.igrowker.miniproject.models.Group;
import com.igrowker.miniproject.models.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {

    @Query("SELECT g FROM Group g JOIN g.users u WHERE u.id = :userId")
    Group findByGroupByUserId(Long userId);

    @Query("SELECT t.group FROM Travel t WHERE t.id = :travelId")
    Group findByGroupByTravelId(@Param("travelId") Long travelId);

    @Query("SELECT t FROM Travel t " +
            "LEFT JOIN t.transports " +
            "LEFT JOIN t.accommodations " +
            "LEFT JOIN t.meals " +
            "LEFT JOIN t.activities " +
            "WHERE t.id = :travelId")
    Optional<Travel> findByIdWithDetails(@Param("travelId") Long travelId);

    @Query("SELECT t FROM Travel t " +
            "LEFT JOIN t.transports " +
            "LEFT JOIN t.accommodations " +
            "LEFT JOIN t.meals " +
            "LEFT JOIN t.activities " +
            "LEFT JOIN t.group g " +            // Join con Group
            "LEFT JOIN g.users u " +            // Join con los usuarios del grupo
            "WHERE u.id = :userId")
    Optional<Travel> findByUserId(@Param("userId") Long userId);


}
