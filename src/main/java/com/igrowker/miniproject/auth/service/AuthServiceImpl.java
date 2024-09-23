package com.igrowker.miniproject.auth.service;

import com.igrowker.miniproject.auth.dto.JwtResponseDto;
import com.igrowker.miniproject.auth.dto.LoginDto;
import com.igrowker.miniproject.auth.dto.RegisterDto;
import com.igrowker.miniproject.auth.jwt.JwtService;
import com.igrowker.miniproject.exceptions.BadCredentialsException;
import com.igrowker.miniproject.exceptions.ConflictException;
import com.igrowker.miniproject.exceptions.NotFoundException;
import com.igrowker.miniproject.models.Role;
import com.igrowker.miniproject.models.User;
import com.igrowker.miniproject.repositories.RoleRepository;
import com.igrowker.miniproject.repositories.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager,
                            JwtService jwtService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getMail())){
            throw new ConflictException("El usuario existe!");
        }
        // Buscar el rol en la base de datos
        Role role = roleRepository.findByName(registerDto.getRole())
                .orElseThrow(() -> new NotFoundException("El rol no existe!"));

        User user = new User();
        user.setName(registerDto.getName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getMail());
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public JwtResponseDto login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getMail(),
                            loginDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateToken(authentication);
            return new JwtResponseDto(token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Credenciales inválidas!");
        }
    }

    @Override
    public Long getIdByLoguedUser(HttpHeaders headers) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("Usuario no encontrado"));
        return user.getId(); // retorna el ID del usuario logueado por medio del JWT
    }
}
