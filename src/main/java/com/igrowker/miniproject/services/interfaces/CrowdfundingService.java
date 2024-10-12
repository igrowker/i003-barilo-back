package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.CrowdfundingDto;
import com.igrowker.miniproject.dtos.DonationDto;
import com.igrowker.miniproject.dtos.req.CreateDonationDto;

import java.util.List;

public interface CrowdfundingService {
    List<CrowdfundingDto> getGroupCrowdfunding(Long groupId);

    List<DonationDto> getGroupCrowdfundingDonations(Long groupId, Long crowdfundingId);

    boolean donateToCrowdfunding(Long crowdfundingId, CreateDonationDto createDonationDto);

    boolean cancelCrowdfunding(Long crowdfundingId);

    CrowdfundingDto updateCrowdfunding(Long groupId, Long crowdfundingId, CrowdfundingDto crowdfundingsDto);
}
