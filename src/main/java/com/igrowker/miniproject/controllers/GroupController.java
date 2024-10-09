package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.GroupDto;
import com.igrowker.miniproject.dtos.req.GetGroupById;
import com.igrowker.miniproject.services.interfaces.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@Tag(name = "Groups", description = "Group API")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(summary = "Save a group")
    @PostMapping
    public ResponseEntity<GroupDto> saveGroup(@RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(groupService.saveGroup(groupDto));
    }

    @Operation(summary = "Get groups by name")
    @GetMapping
    public ResponseEntity<Page<GroupDto>> getGroups(@RequestParam(required = false) String name,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(groupService.getGroups(pageable, name));
    }

    @Operation(summary = "Update a group")
    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable Long groupId, @RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(groupService.updateGroup(groupId, groupDto));
    }

    @Operation(summary = "Add the logged user to a group")
    @PostMapping("/{groupId}/add-user")
    public ResponseEntity<GroupDto> addUserToGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.addUserToGroup(groupId));
    }

    @Operation(summary = "Remove the logged user from a group")
    @DeleteMapping("/{groupId}/remove-user")
    public ResponseEntity<GroupDto> removeUserFromGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.removeUserFromGroup(groupId));
    }

    @Operation(summary = "Get group by id")
    @GetMapping("/{groupId}")
    public ResponseEntity<GetGroupById> getGroupById(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.getGroupById(groupId));
    }

    @GetMapping("{groupId}/crowdfundings")
    public ResponseEntity<List<GetGroupById>> getGroupCrowdfundings(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.getGroupCrowdfundings(groupId));
    }

    @GetMapping("{groupId}/crowdfundings/{crowdfundingId}/donations")
    public ResponseEntity<List<GetGroupById>> getGroupCrowdfundingsDonations(@PathVariable Long groupId, @PathVariable Long crowdfundingId) {
        return ResponseEntity.ok(groupService.getGroupCrowdfundingsDonations(groupId, crowdfundingId));
    }

    @PostMapping("{groupId}/crowdfundings/{crowdfundingId}/donate")
    public ResponseEntity<GroupDto> donateToCrowdfunding(@PathVariable Long groupId, @PathVariable Long crowdfundingId) {
        return ResponseEntity.ok(groupService.donateToCrowdfunding(groupId, crowdfundingId));
    }

    @PostMapping("{groupId}/crowdfundings/{crowdfundingId}/cancel")
    public ResponseEntity<GroupDto> cancelCrowdfunding(@PathVariable Long groupId, @PathVariable Long crowdfundingId) {
        return ResponseEntity.ok(groupService.cancelCrowdfunding(groupId, crowdfundingId));
    }

    @PutMapping("{groupId}/crowdfundings/{crowdfundingId}")
    public ResponseEntity<GroupDto> updateCrowdfunding(@PathVariable Long groupId, @PathVariable Long crowdfundingId, @RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(groupService.updateCrowdfunding(groupId, crowdfundingId, groupDto));
    }


}