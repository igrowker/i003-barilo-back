package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.auth.service.AuthService;
import com.igrowker.miniproject.dtos.PasswordDto;
import com.igrowker.miniproject.dtos.UserDto;
import com.igrowker.miniproject.exceptions.BadRequestException;
import com.igrowker.miniproject.exceptions.NotFoundException;
import com.igrowker.miniproject.models.User;
import com.igrowker.miniproject.repositories.UserRepository;
import com.igrowker.miniproject.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthService authService, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.authService = authService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDto getProfile(HttpHeaders headers) {
        Long userId = authService.getIdByLoguedUser(headers);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("Usuario no encontrado!"));
        modelMapper.typeMap(User.class, UserDto.class)
                .addMapping(role -> role.getRole().getName(), UserDto::setRole);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void updatePassword(HttpHeaders headers, PasswordDto passwordDto) {
        Long userId = authService.getIdByLoguedUser(headers);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("Usuario no encontrado"));
        if (passwordEncoder.matches(passwordDto.getCurrentPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
            userRepository.save(user);
        }else
            throw new BadRequestException("La contraseña no coincide!");

    }
}
