package com.igrowker.miniproject.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.igrowker.miniproject.models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
