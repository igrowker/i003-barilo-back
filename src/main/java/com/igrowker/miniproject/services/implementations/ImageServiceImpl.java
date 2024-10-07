package com.igrowker.miniproject.services.implementations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import com.igrowker.miniproject.mappers.ImageMapper;
import com.igrowker.miniproject.models.Image;
import com.igrowker.miniproject.models.ImageEntityLink;
import com.igrowker.miniproject.models.enums.TypeClass;
import com.igrowker.miniproject.repositories.ImageEntityLinkRepository;
import com.igrowker.miniproject.repositories.ImageRepository;
import com.igrowker.miniproject.services.interfaces.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;
    private final ImageEntityLinkRepository imageEntityLinkRepository;

    @Override
    public List<Image> findByEntity(Long id, TypeClass entityType) {
        return List.of();
    }

    @Override
    public List<Image> findByUser(Long id) {
        return List.of();
    }

    public Optional<Image> findByPublicId(String publicId) {
        return imageRepository.findById(publicId);
    }

    public Optional<Image> upload(MultipartFile multipartFile, Long id, TypeClass entityType) throws IOException {
        if (StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new BadRequestException("La imagen no puede estar vacia");
        }
        Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
        Image uploadedImage = ImageMapper.toImage(uploadResult);
        imageRepository.save(uploadedImage);
        imageEntityLinkRepository.save(ImageEntityLink.builder()
                .image(uploadedImage)
                .entityId(id)
                .entityType(entityType)
                .build());
        return Optional.of(uploadedImage);
    }

    @Override
    public Optional<Image> save(Image image, Long id, TypeClass entityType) {
        imageRepository.save(image);
        imageEntityLinkRepository.save(ImageEntityLink.builder()
                .image(image)
                .entityId(id)
                .entityType(entityType)
                .build());
        return Optional.of(image);
    }

    public Map delete(String publicId) throws IOException {
        if (StringUtils.isBlank(publicId)) {
            throw new BadRequestException("La imagen no puede estar vacia");
        }
        imageRepository.deleteById(publicId);
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

}

