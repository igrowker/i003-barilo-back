package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.req.GetGroupById;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.igrowker.miniproject.dtos.GroupDto;

import java.util.List;

public interface GroupService {
    GroupDto saveGroup(GroupDto groupDto);
    Page<GroupDto> getGroups(Pageable pageable, String name);
    GroupDto updateGroup(Long groupId,GroupDto groupDto);
    GroupDto addUserToGroup(Long groupId);
    GroupDto removeUserFromGroup(Long groupId);
    GetGroupById getGroupById(Long groupId);
}
