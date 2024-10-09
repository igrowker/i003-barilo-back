package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.PasswordDto;
import com.igrowker.miniproject.dtos.UserDto;
import org.springframework.http.HttpHeaders;

public interface UserService {

    public UserDto getProfile(HttpHeaders headers);

    public void updatePassword(HttpHeaders headers, PasswordDto passwordDto);

    public boolean userBelongsToAnyGroup();
}
