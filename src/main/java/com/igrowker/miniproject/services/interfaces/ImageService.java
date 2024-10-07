package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.models.Image;
import com.igrowker.miniproject.models.enums.TypeClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ImageService {

    List<Image> findByEntity(Long id, TypeClass entityType);

    List<Image> findByUser(Long id);

    Optional<Image> findByPublicId(String publicId);

    Optional<Image> save(Image image, Long id, TypeClass entityType);

    Optional<Image> upload(MultipartFile multipartFile, Long id, TypeClass entityType) throws IOException;

    Map delete(String publicId) throws IOException;
}
