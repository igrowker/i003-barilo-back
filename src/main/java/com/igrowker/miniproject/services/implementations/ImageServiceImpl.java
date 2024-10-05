package com.igrowker.miniproject.services.implementations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import com.igrowker.miniproject.mappers.ImageMapper;
import com.igrowker.miniproject.models.Image;
import com.igrowker.miniproject.models.User;
import com.igrowker.miniproject.repositories.ImageRepository;
import com.igrowker.miniproject.services.interfaces.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;

    public Optional<Image> findByPublicId(String publicId) {
        return imageRepository.findById(publicId);
    }

    public Optional<Image> upload(MultipartFile multipartFile, User user) throws IOException {
        if (StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new BadRequestException("La imagen no puede estar vacia");
        }
        Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
        Image uploadedImage = ImageMapper.toImage(uploadResult);
        uploadedImage.setUser(user);
        return Optional.of(imageRepository.save(uploadedImage));
    }

    public Map delete(String publicId) throws IOException {
        if (StringUtils.isBlank(publicId)) {
            throw new BadRequestException("La imagen no puede estar vacia");
        }
        imageRepository.deleteById(publicId);
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

}

