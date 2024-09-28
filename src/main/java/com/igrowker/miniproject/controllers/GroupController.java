package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.GroupDto;
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

    @Operation(summary = "Add users to a group")
    @PostMapping("/{groupId}/users")
    public ResponseEntity<GroupDto> addUsersToGroup(@PathVariable Long groupId, @RequestBody List<Long> userIds) {
        return ResponseEntity.ok(groupService.addUsersToGroup(groupId, userIds));
    }

    @Operation(summary = "Remove users from a group")
    @DeleteMapping("/{groupId}/users")
    public ResponseEntity<GroupDto> removeUsersFromGroup(@PathVariable Long groupId, @RequestBody List<Long> userIds) {
        return ResponseEntity.ok(groupService.removeUsersFromGroup(groupId, userIds));
    }
}