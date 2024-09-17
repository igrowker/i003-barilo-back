package com.igrowker.miniproject.auth.service;

import com.igrowker.miniproject.auth.dto.JwtResponseDto;
import com.igrowker.miniproject.auth.dto.LoginDto;
import com.igrowker.miniproject.auth.dto.RegisterDto;

public interface AuthService {

    public void register(RegisterDto registerDto);

    public JwtResponseDto login(LoginDto loginDto);


}
