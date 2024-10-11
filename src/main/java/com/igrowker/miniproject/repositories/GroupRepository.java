package com.igrowker.miniproject.repositories;

import com.igrowker.miniproject.models.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT g FROM Group g WHERE (:nombre IS NULL OR lower(g.name) LIKE lower(concat('%', :nombre,'%')))")
    Page<Group> findAllByNameContaining(@Param("nombre") String nombre, Pageable pageable);
}