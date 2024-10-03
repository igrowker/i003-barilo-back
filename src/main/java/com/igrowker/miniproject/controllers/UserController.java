package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.PasswordDto;
import com.igrowker.miniproject.dtos.UserDto;
import com.igrowker.miniproject.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@Tag(name = "Profile", description = "Profile API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Allows you to obtain the user's profile")
    @GetMapping
    public ResponseEntity<UserDto> getProfile(@RequestHeader HttpHeaders headers){
        return new ResponseEntity<>(userService.getProfile(headers), HttpStatus.OK);
    }

    @Operation(summary = "Allows you to change the password")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestHeader HttpHeaders headers, @RequestBody PasswordDto passwordDto){
        userService.updatePassword(headers, passwordDto);
        return new ResponseEntity<>("Password actualizada!", HttpStatus.OK);
    }
}
