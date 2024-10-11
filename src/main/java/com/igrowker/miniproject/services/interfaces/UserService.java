package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.PasswordDto;
import com.igrowker.miniproject.dtos.UserDto;

import com.igrowker.miniproject.dtos.req.UpdateUserDto;
import com.igrowker.miniproject.models.Image;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;

public interface UserService {

    public UserDto getProfile(HttpHeaders headers);

    public void updatePassword(HttpHeaders headers, PasswordDto passwordDto);

    public UserDto updateProfile(Long id, UpdateUserDto userDto, MultipartFile image) throws IOException;

    Optional<Image> uploadImage(MultipartFile multipartFile, Long id) throws IOException;

    public boolean userBelongsToAnyGroup();

}
