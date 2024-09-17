package com.igrowker.miniproject.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    private String name;
    private String mail;
    private String password;
    private String role;
}
