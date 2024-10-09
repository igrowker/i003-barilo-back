package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.CrowdfundingDto;
import com.igrowker.miniproject.dtos.DonationDto;
import com.igrowker.miniproject.services.interfaces.CrowdfundingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups/{groupId}/crowdfunding")
@RequiredArgsConstructor
public class CrowdfundingController {

    private final CrowdfundingService crowdfundingService;

    @GetMapping
    public ResponseEntity<CrowdfundingDto> getGroupCrowdfundings(@PathVariable Long groupId) {
        return ResponseEntity.ok(crowdfundingService.getGroupCrowdfunding(groupId));
    }

    @GetMapping("/{crowdfundingId}/donations")
    public ResponseEntity<List<DonationDto>> getGroupCrowdfundingsDonations(@PathVariable Long groupId, @PathVariable Long crowdfundingId) {
        return ResponseEntity.ok(crowdfundingService.getGroupCrowdfundingDonations(groupId, crowdfundingId));
    }

    @PostMapping("/{crowdfundingId}/donate")
    public ResponseEntity<Boolean> donateToCrowdfunding(@PathVariable Long groupId, @PathVariable Long crowdfundingId) {
        return ResponseEntity.ok(crowdfundingService.donateToCrowdfunding(groupId, crowdfundingId));
    }

    @PostMapping("/{crowdfundingId}/cancel")
    public ResponseEntity<Boolean> cancelCrowdfunding(@PathVariable Long groupId, @PathVariable Long crowdfundingId) {
        return ResponseEntity.ok(crowdfundingService.cancelCrowdfunding(groupId, crowdfundingId));
    }

    @PutMapping("/{crowdfundingId}")
    public ResponseEntity<CrowdfundingDto> updateCrowdfunding(@PathVariable Long groupId, @PathVariable Long crowdfundingId, @RequestBody CrowdfundingDto crowdfundingDto) {
        return ResponseEntity.ok(crowdfundingService.updateCrowdfunding(groupId, crowdfundingId, crowdfundingDto));
    }
}
