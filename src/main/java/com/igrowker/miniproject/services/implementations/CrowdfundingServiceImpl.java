package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.dtos.CrowdfundingDto;
import com.igrowker.miniproject.dtos.DonationDto;
import com.igrowker.miniproject.services.interfaces.CrowdfundingService;

import java.util.List;

public class CrowdfundingServiceImpl implements CrowdfundingService {
    @Override
    public CrowdfundingDto getGroupCrowdfunding(Long groupId) {
        return null;
    }

    @Override
    public List<DonationDto> getGroupCrowdfundingDonations(Long groupId, Long crowdfundingId) {
        return List.of();
    }

    @Override
    public boolean donateToCrowdfunding(Long groupId, Long crowdfundingId) {
        return false;
    }

    @Override
    public boolean cancelCrowdfunding(Long groupId, Long crowdfundingId) {
        return false;
    }

    @Override
    public CrowdfundingDto updateCrowdfunding(Long groupId, Long crowdfundingId, CrowdfundingDto groupDto) {
        return null;
    }
}
