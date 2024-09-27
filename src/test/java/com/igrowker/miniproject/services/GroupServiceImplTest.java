package com.igrowker.miniproject.services;

import com.igrowker.miniproject.dtos.GroupDto;
import com.igrowker.miniproject.exceptions.NotFoundException;
import com.igrowker.miniproject.models.Group;
import com.igrowker.miniproject.models.User;
import com.igrowker.miniproject.repositories.GroupRepository;
import com.igrowker.miniproject.repositories.UserRepository;
import com.igrowker.miniproject.services.implementations.GroupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    void saveGroupSuccessfully() {
        GroupDto groupDto = new GroupDto();
        Group group = new Group();

        when(modelMapper.map(groupDto, Group.class)).thenReturn(group);
        when(groupRepository.save(group)).thenReturn(group);
        when(modelMapper.map(group, GroupDto.class)).thenReturn(groupDto);

        GroupDto result = groupService.saveGroup(groupDto);

        assertEquals(groupDto, result);
        verify(groupRepository, times(1)).save(group);
    }

    @Test
    void getGroupsSuccessfully() {

        String name = "grupo1";
        Group group = new Group();
        group.setName(name);
        Page<Group> page = new PageImpl<>(Collections.singletonList(group));
        Pageable pageable = PageRequest.of(0, 5);

        when(groupRepository.findAllByNameContaining(name, pageable)).thenReturn(page);
        when(modelMapper.map(group, GroupDto.class)).thenReturn(new GroupDto());

        Page<GroupDto> result = groupService.getGroups(pageable, name);

        assertNotNull(result);
        verify(groupRepository, times(1)).findAllByNameContaining(name, pageable);
    }

    @Test
    void updateGroupSuccessfully() {
        Long groupId = 1L;
        GroupDto groupDto = new GroupDto();
        groupDto.setName("updatedName");
        Group existingGroup = new Group();
        existingGroup.setName("oldName");

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(existingGroup));
        when(groupRepository.save(existingGroup)).thenReturn(existingGroup);
        when(modelMapper.map(existingGroup, GroupDto.class)).thenReturn(groupDto);

        GroupDto result = groupService.updateGroup(groupId, groupDto);

        assertEquals(groupDto, result);
        assertEquals("updatedName", existingGroup.getName());
        verify(groupRepository, times(1)).save(existingGroup);
    }

    @Test
    void updateGroupNotFound() {
        Long groupId = 1L;
        GroupDto groupDto = new GroupDto();

        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> groupService.updateGroup(groupId, groupDto));
    }

    @Test
    void addUsersToGroupSuccessfully() {
        Long groupId = 1L;
        Long userId = 2L;
        Group group = new Group();
        User user = new User();
        GroupDto groupDto = new GroupDto();

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(groupRepository.save(group)).thenReturn(group);
        when(modelMapper.map(group, GroupDto.class)).thenReturn(groupDto);

        GroupDto result = groupService.addUsersToGroup(groupId, Arrays.asList(userId));

        assertEquals(groupDto, result);
        assertTrue(group.getUsers().contains(user));
        verify(groupRepository, times(1)).save(group);
    }

    @Test
    void removeUsersFromGroupSuccessfully() {
        Long groupId = 1L;
        Long userId = 2L;
        Group group = new Group();
        User user = new User();
        group.getUsers().add(user);
        GroupDto groupDto = new GroupDto();

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(groupRepository.save(group)).thenReturn(group);
        when(modelMapper.map(group, GroupDto.class)).thenReturn(groupDto);

        GroupDto result = groupService.removeUsersFromGroup(groupId, Arrays.asList(userId));

        assertEquals(groupDto, result);
        assertFalse(group.getUsers().contains(user));
        verify(groupRepository, times(1)).save(group);
    }
}