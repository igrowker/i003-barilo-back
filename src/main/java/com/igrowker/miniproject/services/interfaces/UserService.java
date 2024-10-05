package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.PasswordDto;
import com.igrowker.miniproject.dtos.UserDto;
import com.igrowker.miniproject.models.Image;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {

    public UserDto getProfile(HttpHeaders headers);

    public void updatePassword(HttpHeaders headers, PasswordDto passwordDto);

    Optional<Image> uploadImage(MultipartFile multipartFile, Long id) throws IOException;
}
