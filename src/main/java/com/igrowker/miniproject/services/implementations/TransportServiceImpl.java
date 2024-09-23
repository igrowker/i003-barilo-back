package com.igrowker.miniproject.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.igrowker.miniproject.dtos.TransportDto;
import com.igrowker.miniproject.dtos.filters.TransportFilterDto;
import com.igrowker.miniproject.dtos.req.CreateTransportDto;
import com.igrowker.miniproject.exceptions.NotFoundException;
import com.igrowker.miniproject.models.Transport;
import com.igrowker.miniproject.repositories.TransportRepository;
import com.igrowker.miniproject.services.interfaces.TransportService;
import com.igrowker.miniproject.specifications.TransportSpecifications;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransportServiceImpl implements TransportService {

    private final TransportRepository transportRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<TransportDto> getAllTransports(TransportFilterDto transportFilterDto, Pageable pageable) {
        Specification<Transport> specification = TransportSpecifications.withFilter(transportFilterDto);
        Page<Transport> transports = transportRepository.findAll(specification, pageable);
        return transports.map(transport -> modelMapper.map(transport, TransportDto.class));
    }

    @Override
    public TransportDto getTransportById(Long id) {
        Transport transport = transportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el transporte con el id: " + id));
        return modelMapper.map(transport, TransportDto.class);
    }

    @Override
    public TransportDto createTransport(CreateTransportDto transportDto) {
        Transport transport = modelMapper.map(transportDto, Transport.class);
        Transport savedTransport = transportRepository.save(transport);
        return modelMapper.map(savedTransport, TransportDto.class);
    }

    @Override
    public TransportDto updateTransport(Long id, TransportDto transportDto) {
        Transport transport = transportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el transporte con el id: " + id));
        modelMapper.map(transportDto, transport);
        Transport updatedTransport = transportRepository.save(transport);
        return modelMapper.map(updatedTransport, TransportDto.class);
    }

    @Override
    public void deleteTransport(Long id) {
        Transport transport = transportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el transporte con el id: " + id));
        transportRepository.delete(transport);
    }

}
