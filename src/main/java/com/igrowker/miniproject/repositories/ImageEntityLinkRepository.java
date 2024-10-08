package com.igrowker.miniproject.repositories;

import com.igrowker.miniproject.models.ImageEntityLink;
import com.igrowker.miniproject.models.enums.TypeClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageEntityLinkRepository extends JpaRepository<ImageEntityLink, Long> {
    List<ImageEntityLink> findByEntityTypeAndEntityId(TypeClass entityType, Long entityId);

    void deleteByImagePublicId(String publicId);
}
