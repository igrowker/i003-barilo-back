package com.igrowker.miniproject.repositories;

import com.igrowker.miniproject.models.Crowdfunding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrowdfundingRepository extends JpaRepository<Crowdfunding, Long> {
}
