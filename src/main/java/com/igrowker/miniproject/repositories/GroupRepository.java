package com.igrowker.miniproject.repositories;

import com.igrowker.miniproject.models.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long>  {
    Page<Group> findAllByNameContaining(String nombre, Pageable pageable);
}
