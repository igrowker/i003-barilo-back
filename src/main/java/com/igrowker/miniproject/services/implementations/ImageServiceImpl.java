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
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;
    private final ImageEntityLinkRepository imageEntityLinkRepository;

    @Override
    public List<Image> findByEntityIdAndEntityType(Long id, TypeClass entityType) {
        List<ImageEntityLink> imageEntityLinks = imageEntityLinkRepository.findByEntityTypeAndEntityId(entityType, id);
        if (imageEntityLinks.isEmpty()) {
            return List.of();
        }
        return imageEntityLinks.stream().map(ImageEntityLink::getImage).toList();
    }

    @Override
    public List<Image> findByUser(Long id) {
        List<ImageEntityLink> imageEntityLinks = imageEntityLinkRepository.findByEntityTypeAndEntityId(TypeClass.USER, id);
        return imageEntityLinks.stream().map(ImageEntityLink::getImage).toList();
    }

    public Optional<Image> findByPublicId(String publicId) {
        return imageRepository.findById(publicId);
    }

    public Optional<Image> upload(MultipartFile multipartFile, Long id, TypeClass entityType) throws IOException {
        if (StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new BadRequestException("La imagen no puede estar vacia");
        }

        String etag = DigestUtils.md5Hex(multipartFile.getInputStream()); //pre-calculando el etag evito duplicaciones en cloudinary
        Map<String, Object> params = ObjectUtils.asMap(
                "public_id", etag,
                "overwrite", false
        );
        Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), params);
        Image uploadedImage = ImageMapper.toImage(uploadResult);

        Optional<Image> existingImage = imageRepository.findById(uploadedImage.getPublicId());
        if (existingImage.isPresent()) {
            uploadedImage = existingImage.get();
        } else {
            imageRepository.save(uploadedImage);
        }

        boolean linkExists = imageEntityLinkRepository.findByEntityTypeAndEntityId(entityType, id).isEmpty();
        if (linkExists) {
            imageEntityLinkRepository.save(ImageEntityLink.builder()
                    .image(uploadedImage)
                    .entityId(id)
                    .entityType(entityType)
                    .build());
        }

        return Optional.of(uploadedImage);
    }

    @Override
    public Optional<Image> save(Image image, Long id, TypeClass entityType) {
        image.setPublicId(UUID.randomUUID().toString());
        imageRepository.save(image);
        imageEntityLinkRepository.save(ImageEntityLink.builder()
                .image(image)
                .entityId(id)
                .entityType(entityType)
                .build());
        return Optional.of(image);
    }

    @Transactional
    public Map delete(String publicId) throws IOException {
        if (StringUtils.isBlank(publicId)) {
            throw new BadRequestException("La imagen no puede estar vacia");
        }
        imageEntityLinkRepository.deleteByImagePublicId(publicId);
        imageRepository.deleteById(publicId);
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

}

