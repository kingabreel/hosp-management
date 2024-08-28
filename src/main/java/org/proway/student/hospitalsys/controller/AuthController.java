package org.proway.student.hospitalsys.controller;

import org.proway.student.hospitalsys.domain.Role;
import org.proway.student.hospitalsys.domain.User;
import org.proway.student.hospitalsys.domain.dto.LoginDto;
import org.proway.student.hospitalsys.domain.dto.RegisterDto;
import org.proway.student.hospitalsys.domain.response.LoginResponseDto;
import org.proway.student.hospitalsys.infra.security.TokenService;
import org.proway.student.hospitalsys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.gerenateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto data){
        if(this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encPass = new BCryptPasswordEncoder().encode(data.password());

        this.userRepository.save(new User(data.name(), data.age(), data.email(), encPass, Role.PATIENT));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/doctor")
    public ResponseEntity registerDoctor(@RequestBody @Valid RegisterDto data){
        if(this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encPass = new BCryptPasswordEncoder().encode(data.password());

        this.userRepository.save(new User(data.name(), data.age(), data.email(), encPass, Role.DOCTOR));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/receptionist")
    public ResponseEntity registerReceptionist(@RequestBody @Valid RegisterDto data){
        if(this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encPass = new BCryptPasswordEncoder().encode(data.password());

        this.userRepository.save(new User(data.name(), data.age(), data.email(), encPass, Role.RECEPTIONIST));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token){
        tokenService.invalidateToken(token);
        return ResponseEntity.ok("Logged out");
    }
}
