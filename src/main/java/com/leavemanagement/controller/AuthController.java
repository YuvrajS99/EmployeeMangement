package com.leavemanagement.controller;

import com.leavemanagement.dto.AuthRequest;
import com.leavemanagement.dto.AuthResponse;
import com.leavemanagement.dto.RegisterRequest;
import com.leavemanagement.entity.Role;
import com.leavemanagement.entity.User;
import com.leavemanagement.repository.UserRepository;
import com.leavemanagement.security.CustomUserDetails;
import com.leavemanagement.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 username taken
        }

        Role role = (request.getRole() != null) ? request.getRole() : Role.EMPLOYEE;

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), role.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(
                userDetails.getUsername(),
                userDetails.getUser().getRole().name()
        );
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
