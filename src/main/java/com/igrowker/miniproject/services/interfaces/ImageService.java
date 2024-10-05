package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.models.Image;
import com.igrowker.miniproject.models.User;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface ImageService {
    Optional<Image> findByPublicId(String publicId);

    Optional<Image> upload(MultipartFile multipartFile, User user) throws IOException;

    Map delete(String publicId) throws IOException;
}
