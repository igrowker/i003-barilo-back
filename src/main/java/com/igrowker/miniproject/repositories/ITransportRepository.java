package com.igrowker.miniproject.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igrowker.miniproject.models.Transport;

@Repository
public interface ITransportRepository extends JpaRepository<Transport, Long> {
    Page<Transport> findAll(Specification<Transport> specification, Pageable pageable);
}