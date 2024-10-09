package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.CrowdfundingDto;
import com.igrowker.miniproject.dtos.DonationDto;
import com.igrowker.miniproject.models.Donation;

import java.util.List;

public interface CrowdfundingService {
    CrowdfundingDto getGroupCrowdfunding(Long groupId);

    List<DonationDto> getGroupCrowdfundingDonations(Long groupId, Long crowdfundingId);

    boolean donateToCrowdfunding(Long groupId, Long crowdfundingId);

    boolean cancelCrowdfunding(Long groupId, Long crowdfundingId);

    CrowdfundingDto updateCrowdfunding(Long groupId, Long crowdfundingId, CrowdfundingDto groupDto);
}
