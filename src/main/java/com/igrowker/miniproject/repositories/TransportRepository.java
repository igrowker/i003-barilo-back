package com.igrowker.miniproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.igrowker.miniproject.models.Transport;

@Repository
public interface ITransportRepository extends JpaRepository<Transport, Long>, JpaSpecificationExecutor<Transport> {
}