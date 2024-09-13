package com.igrowker.miniproject.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String mail;
    private String password;
}
