package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.auth.service.AuthService;
import com.igrowker.miniproject.dtos.PasswordDto;
import com.igrowker.miniproject.dtos.UserDto;
import com.igrowker.miniproject.dtos.req.UpdateUserDto;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRole(user.getRole().getName());

        if (user.getImageId() != null) {
            Image image = imageService.findByPublicId(user.getImageId()).orElse(null);
            userDto.setImage(image);
        }
        return userDto;
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
    public UserDto updateProfile(Long id, UpdateUserDto userDto, MultipartFile image) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        if (image != null) {
            uploadImage(user, image);
        } else {
            updateImageUrl(user, userDto);
        }

        updateUserProfile(user, userDto);

        userRepository.save(user);

        UserDto newUserDto = modelMapper.map(user, UserDto.class);
        newUserDto.setRole(user.getRole().getName());

        if (user.getImageId() != null) {
            Image newImage = imageService.findByPublicId(user.getImageId()).orElse(null);
            newUserDto.setImage(newImage);
        }

        return newUserDto;
    }

    private void uploadImage(User user, MultipartFile image) throws IOException {
        Optional<Image> uploadedImage = imageService.upload(image, user.getId(), TypeClass.USER);
        // verifico si la imagen es diferente a la de la imagen actual basdo en publicId
        if (user.getImageId() != null && uploadedImage.isPresent() && !uploadedImage.get().getPublicId().equals(user.getImageId())) {
            Optional<Image> preImage = imageService.findByPublicId(user.getImageId());
            preImage.ifPresent(image1 -> {
                try {
                    imageService.delete(image1.getPublicId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        user.setImageId(uploadedImage.orElseThrow(() -> new NotFoundException("Imagen no encontrada")).getPublicId());
    }

    private void updateImageUrl(User user, UpdateUserDto userDto) throws IOException {
        if (userDto == null || !hasValue(userDto.getImageUrl())) {
            return;
        }
        Image uploadedImage = Image.builder()
                .url(userDto.getImageUrl())
                .build();
        Optional<Image> newImage = imageService.save(uploadedImage, user.getId(), TypeClass.USER);
        if (user.getImageId() != null && newImage.isPresent() && !newImage.get().getPublicId().equals(user.getImageId())) {
            Optional<Image> preImage = imageService.findByPublicId(user.getImageId());
            preImage.ifPresent(image1 -> {
                try {
                    imageService.delete(image1.getPublicId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        user.setImageId(newImage.orElseThrow(() -> new NotFoundException("Imagen no encontrada")).getPublicId());
    }

    private void updateUserProfile(User user, UpdateUserDto userDto) {
        if (userDto != null) {
            if (hasValue(userDto.getName())) {
                user.setName(userDto.getName());
            }
            if (hasValue(userDto.getEmail())) {
                user.setEmail(userDto.getEmail());
            }
            if (hasValue(userDto.getPhone())) {
                user.setPhone(userDto.getPhone());
            }
        }
    }

    private boolean hasValue(String value) {
        return value != null && !value.isEmpty() && !value.isBlank();
    }

    @Override
    public Optional<Image> uploadImage(MultipartFile multipartFile, Long id) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        Optional<Image> image = imageService.upload(multipartFile, user.getId(), TypeClass.USER);
        user.setImageId(image.orElseThrow(() -> new NotFoundException("Imagen no encontrada")).getPublicId());
        userRepository.save(user);
        return image;
    }

    public boolean userBelongsToAnyGroup() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("Usuario con email "+  username + "no encontrado"));
        return !user.getGroups().isEmpty();
    }

}
