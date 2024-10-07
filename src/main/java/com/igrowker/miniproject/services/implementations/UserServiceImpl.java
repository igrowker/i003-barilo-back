package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.auth.service.AuthService;
import com.igrowker.miniproject.dtos.PasswordDto;
import com.igrowker.miniproject.dtos.UserDto;
import com.igrowker.miniproject.exceptions.BadRequestException;
import com.igrowker.miniproject.exceptions.NotFoundException;
import com.igrowker.miniproject.models.Image;
import com.igrowker.miniproject.models.User;
import com.igrowker.miniproject.models.enums.TypeClass;
import com.igrowker.miniproject.repositories.UserRepository;
import com.igrowker.miniproject.services.interfaces.ImageService;
import com.igrowker.miniproject.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public UserServiceImpl(UserRepository userRepository, AuthService authService, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder, ImageService imageService) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    @Override
    public UserDto getProfile(HttpHeaders headers) {
        Long userId = authService.getIdByLoguedUser(headers);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado!"));
        modelMapper.typeMap(User.class, UserDto.class)
                .addMapping(role -> role.getRole().getName(), UserDto::setRole);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void updatePassword(HttpHeaders headers, PasswordDto passwordDto) {
        Long userId = authService.getIdByLoguedUser(headers);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        if (passwordEncoder.matches(passwordDto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
            userRepository.save(user);
        } else
            throw new BadRequestException("La contraseña no coincide!");

    }

    @Override
    public Optional<Image> uploadImage(MultipartFile multipartFile, Long id) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return imageService.upload(multipartFile, user.getId(), TypeClass.USER);
    }
}
