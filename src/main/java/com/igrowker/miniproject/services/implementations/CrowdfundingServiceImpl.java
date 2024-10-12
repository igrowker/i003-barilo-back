package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.dtos.CrowdfundingDto;
import com.igrowker.miniproject.dtos.DonationDto;
import com.igrowker.miniproject.dtos.req.CreateDonationDto;
import com.igrowker.miniproject.models.Crowdfunding;
import com.igrowker.miniproject.models.Donation;
import com.igrowker.miniproject.models.Group;
import com.igrowker.miniproject.repositories.CrowdfundingRepository;
import com.igrowker.miniproject.repositories.GroupRepository;
import com.igrowker.miniproject.services.interfaces.CrowdfundingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrowdfundingServiceImpl implements CrowdfundingService {

    private final GroupRepository groupRepository;
    private final CrowdfundingRepository crowdfundingRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CrowdfundingDto> getGroupCrowdfunding(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("No se encontro el grupo"));
        return group.getUsers().stream()
                .flatMap(user -> user.getCrowdfundings().stream())
                .map(crowdfunding -> modelMapper.map(crowdfunding, CrowdfundingDto.class))
                .toList();
    }

    @Override
    public List<DonationDto> getGroupCrowdfundingDonations(Long groupId, Long crowdfundingId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("No se encontro el grupo"));
        return group.getUsers().stream()
                .flatMap(user -> user.getCrowdfundings().stream())
                .filter(crowdfunding -> crowdfunding.getId().equals(crowdfundingId))
                .flatMap(crowdfunding -> crowdfunding.getDonations().stream())
                .map(donation -> modelMapper.map(donation, DonationDto.class))
                .toList();
    }

    @Override
    public boolean donateToCrowdfunding(Long crowdfundingId, CreateDonationDto createDonationDto) {
        Crowdfunding crowdfunding = crowdfundingRepository.findById(crowdfundingId).orElseThrow(() -> new IllegalArgumentException("No se encontro el crowdfundings"));
        if (!crowdfunding.isActive()) {
            throw new IllegalArgumentException("La campaña de crowdfunding ha sido cancelada");
        }
        Donation donation = this.createDonation(createDonationDto, crowdfunding);

        return this.addDonation(crowdfunding, donation);
    }

    private Donation createDonation(CreateDonationDto createDonationDto, Crowdfunding crowdfunding) {
        return Donation.builder()
                .name(createDonationDto.getName())
                .lastName(createDonationDto.getLastName())
                .crowdfunding(crowdfunding)
                .email(createDonationDto.getEmail())
                .paymentMethod(createDonationDto.getPaymentMethod())
                .cbu(createDonationDto.getCbu())
                .cvu(createDonationDto.getCvu())
                .amount(createDonationDto.getAmount())
                .date(LocalDate.now())
                .build();
    }

    private boolean addDonation(Crowdfunding crowdfunding, Donation donation) {
        try {
            crowdfunding.getDonations().add(donation);
            crowdfundingRepository.save(crowdfunding);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean cancelCrowdfunding(Long crowdfundingId) {
        return crowdfundingRepository.findById(crowdfundingId)
                .map(crowdfunding -> {
                    crowdfunding.setActive(false);
                    crowdfundingRepository.save(crowdfunding);
                    return true;
                })
                .orElse(false); 
    }

    @Override
    public CrowdfundingDto updateCrowdfunding(Long groupId, Long crowdfundingId, CrowdfundingDto crowdfundingDto) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("No se encontro el grupo"));
        Crowdfunding crowdfunding = group.getUsers().stream()
                .flatMap(user -> user.getCrowdfundings().stream())
                .filter(crowdfundings -> crowdfundingId.equals(crowdfundings.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontro el crowdfundings"));

        modelMapper.map(crowdfundingDto, crowdfunding);

        return modelMapper.map(crowdfundingRepository.save(crowdfunding), CrowdfundingDto.class);
    }

}
