package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.PasswordDto;
import com.igrowker.miniproject.dtos.UserDto;
import com.igrowker.miniproject.dtos.req.UpdateUserDto;
import com.igrowker.miniproject.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/profile")
@Tag(name = "Profile", description = "Profile API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Allows you to obtain the user's profile")
    @GetMapping
    public ResponseEntity<UserDto> getProfile(@RequestHeader HttpHeaders headers) {
        return new ResponseEntity<>(userService.getProfile(headers), HttpStatus.OK);
    }

    @Operation(summary = "Allows you to change the password")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestHeader HttpHeaders headers, @RequestBody PasswordDto passwordDto) {
        userService.updatePassword(headers, passwordDto);
        return new ResponseEntity<>("Password actualizada!", HttpStatus.OK);
    }

    @Operation(summary = "Allows you to update your profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")})
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestPart(value = "image", required = false) MultipartFile multipartFile,
                                           @ParameterObject @ModelAttribute UpdateUserDto  userDto) throws IOException {
        return new ResponseEntity<>(userService.updateProfile(id, userDto, multipartFile), HttpStatus.OK);
    }

    @Operation(summary = "Allows you to upload an image")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Image uploaded successfully"), @ApiResponse(responseCode = "400", description = "Invalid parameters")})
    @PostMapping("/{id}/image")
    public ResponseEntity<?> uploadImage(@RequestPart("image") MultipartFile multipartFile, @PathVariable Long id) throws IOException {
        return new ResponseEntity<>(userService.uploadImage(multipartFile, id), HttpStatus.CREATED);
    }
}
