package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.dtos.AccommodationDto;
import com.igrowker.miniproject.dtos.filters.AccommodationFilterDto;
import com.igrowker.miniproject.exceptions.NotFoundException;
import com.igrowker.miniproject.models.Accommodation;
import com.igrowker.miniproject.repositories.AccommodationRepository;
import com.igrowker.miniproject.services.interfaces.AccommodationService;
import com.igrowker.miniproject.services.interfaces.ImageService;
import com.igrowker.miniproject.specifications.AccommodationSpecifications;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Override
    public Page<AccommodationDto> getAllAccommodations(AccommodationFilterDto filter, Pageable pageable) {
        Specification<Accommodation> specification = AccommodationSpecifications.withFilter(filter);
        Page<Accommodation> accommodations = accommodationRepository.findAll(specification, pageable);
        return accommodations.map(accommodation -> {
            AccommodationDto accommodationDto = modelMapper.map(accommodation, AccommodationDto.class);
            accommodationDto.setImage(accommodation.getImageId() != null ? imageService.findByPublicId(accommodation.getImageId()).orElse(null) : null);
            return accommodationDto;
        });
    }

    @Override
    public AccommodationDto getAccommodationById(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el alojamiento con el id: " + id));
        return modelMapper.map(accommodation, AccommodationDto.class);
    }

}
