package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.dtos.GroupDto;
import com.igrowker.miniproject.dtos.req.GetGroupById;
import com.igrowker.miniproject.exceptions.NotFoundException;
import com.igrowker.miniproject.models.Group;
import com.igrowker.miniproject.models.User;
import com.igrowker.miniproject.repositories.GroupRepository;
import com.igrowker.miniproject.repositories.UserRepository;
import com.igrowker.miniproject.services.interfaces.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public GroupDto saveGroup(GroupDto groupDto) {
        return modelMapper.map(groupRepository.save(modelMapper.map(groupDto, Group.class)), GroupDto.class);
    }

    @Override
    public Page<GroupDto> getGroups(Pageable pageable, String name) {
        return groupRepository.findAllByNameContaining(name, pageable).map(group -> modelMapper.map(group, GroupDto.class));
    }

    @Override
    public GroupDto updateGroup(Long groupId, GroupDto groupDto) {
            return groupRepository.findById(groupId)
                    .map(existingGroup -> {
                        if (groupDto.getName() != null && !groupDto.getName().isEmpty()) {
                            existingGroup.setName(groupDto.getName());
                        }
                        Group updatedGroup = groupRepository.save(existingGroup);

                        return modelMapper.map(updatedGroup, GroupDto.class);
                    })
                    .orElseThrow(() -> new NotFoundException("Grupo con id" + groupId + " no encontrado"));
    }

    @Override
    public GroupDto addUserToGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Grupo con id" + groupId + " no encontrado"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("Usuario con email "+  username + "no encontrado" + ". No es posible registrar la actividad realizada"));

        List<User> groupUsers = group.getUsers();
        if (groupUsers.contains(user)) {
            throw new IllegalArgumentException("El usuario ya pertenece al grupo");
        }

        group.getUsers().add(user);

        Group updatedGroup = groupRepository.save(group);
        return modelMapper.map(updatedGroup, GroupDto.class);
    }

    @Override
    public GroupDto removeUsersFromGroup(Long groupId, List<Long> userIds) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Grupo con id " + groupId + " no encontrado"));

        List<User> users = group.getUsers();

        for (Long userId : userIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("Usuario con id " + userId + " no encontrado"));
            users.remove(user);
        }

        group.setUsers(users);
        Group updatedGroup = groupRepository.save(group);

        return modelMapper.map(updatedGroup, GroupDto.class);
    }

    @Override
    public GetGroupById getGroupById(Long groupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("Usuario con email "+  username + "no encontrado"));

        return groupRepository.findById(groupId)
                .map(group -> {
                    GetGroupById groupDto = modelMapper.map(group, GetGroupById.class);
                    groupDto.setIsMember(group.getUsers().contains(user));
                    return groupDto;
                })
                .orElseThrow(() -> new NotFoundException("Grupo con id " + groupId + " no encontrado"));
    }
}
