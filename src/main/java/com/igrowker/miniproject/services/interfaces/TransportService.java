package com.igrowker.miniproject.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.igrowker.miniproject.dtos.TransportDto;
import com.igrowker.miniproject.dtos.filters.TransportFilterDto;
import com.igrowker.miniproject.dtos.req.CreateTransportDto;

public interface TransportService {
    Page<TransportDto> getAllTransports(TransportFilterDto transportFilterDto, Pageable pageable);
    TransportDto getTransportById(Long id);
    TransportDto createTransport(CreateTransportDto transportDto);
    TransportDto updateTransport(Long id, TransportDto transportDto);
    void deleteTransport(Long id);
}