package com.igrowker.miniproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.igrowker.miniproject.models.Transport;

import java.util.List;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long>, JpaSpecificationExecutor<Transport> {

    public List<Transport> findAllByDestinationId(Long destinationId);
}