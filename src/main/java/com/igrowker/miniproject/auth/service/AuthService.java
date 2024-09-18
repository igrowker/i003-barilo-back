package com.igrowker.miniproject.auth.service;

import com.igrowker.miniproject.auth.dto.JwtResponseDto;
import com.igrowker.miniproject.auth.dto.LoginDto;
import com.igrowker.miniproject.auth.dto.RegisterDto;
import com.igrowker.miniproject.dtos.UserDto;
import org.springframework.http.HttpHeaders;


public interface AuthService {

    public void register(RegisterDto registerDto);

    public JwtResponseDto login(LoginDto loginDto);

    public Long getIdByLoguedUser(HttpHeaders headers);
}
