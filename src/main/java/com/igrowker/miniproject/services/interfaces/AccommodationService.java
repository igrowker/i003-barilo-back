package com.igrowker.miniproject.services.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.igrowker.miniproject.dtos.AccommodationDto;
import com.igrowker.miniproject.dtos.filters.AccommodationFilterDto;

public interface AccommodationService {

    Page<AccommodationDto> getAllAccommodations(AccommodationFilterDto filter, Pageable pageable);

    AccommodationDto getAccommodationById(Long id);
}
