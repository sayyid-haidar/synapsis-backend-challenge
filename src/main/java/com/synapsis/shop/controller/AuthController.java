package com.synapsis.shop.controller;

import java.util.logging.Logger;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.synapsis.shop.dbo.User;
import com.synapsis.shop.dto.AuthRequest;
import com.synapsis.shop.dto.AuthResponse;
import com.synapsis.shop.dto.RefreshTokenRequest;
import com.synapsis.shop.dto.UserDTO;
import com.synapsis.shop.exception.BadRequestException;
import com.synapsis.shop.exception.InvalidCredentialException;
import com.synapsis.shop.repository.UserRepository;
import com.synapsis.shop.util.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    private Logger logger = Logger.getLogger(AuthController.class.getName());

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) throws Exception {
        User user = this.userRepository.findOneByEmail(request.getEmail()).orElse(null);

        if (user == null || !BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException();
        }

        user.setRefreshToken(RandomStringUtils.randomAlphanumeric(30));
        this.userRepository.save(user);

        String token = this.jwtUtil.generateToken(user);

        AuthResponse response = AuthResponse.builder()
                .email(user.getEmail())
                .userId(user.getId())
                .refreshToken(user.getRefreshToken())
                .token(token)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registration(@RequestBody @Valid AuthRequest request) throws Exception {
        boolean isEmailExist = this.userRepository.findOneByEmail(request.getEmail()).isPresent();
        if (isEmailExist) {
            throw new BadRequestException();
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .refreshToken(RandomStringUtils.randomAlphanumeric(30))
                .build();

        this.userRepository.save(user);

        String token = this.jwtUtil.generateToken(user);

        AuthResponse response = AuthResponse.builder()
                .email(user.getEmail())
                .userId(user.getId())
                .refreshToken(user.getRefreshToken())
                .token(token)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestHeader("Authorization") String authToken,
            @RequestBody @Valid RefreshTokenRequest request) throws Exception {

        String jwt = authToken.replace("Bearer ", "");
        UserDTO userDTO = this.jwtUtil.parseToken(jwt);

        User user = this.userRepository.findById(userDTO.getId()).orElse(null);

        if (user == null || !user.getRefreshToken().equalsIgnoreCase(request.getRefreshToken())) {
            throw new BadRequestException();
        }

        user.setRefreshToken(RandomStringUtils.randomAlphanumeric(30));
        this.userRepository.save(user);

        String token = this.jwtUtil.generateToken(user);

        AuthResponse response = AuthResponse.builder()
                .email(user.getEmail())
                .userId(user.getId())
                .refreshToken(user.getRefreshToken())
                .token(token)
                .build();

        return ResponseEntity.ok().body(response);
    }

}
