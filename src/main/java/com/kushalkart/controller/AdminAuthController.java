package com.kushalkart.controller;

import com.kushalkart.entity.AdminUser;
import com.kushalkart.repository.AdminUserRepository;
import com.kushalkart.util.AdminJwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminAuthController {

    private final AuthenticationManager authManager;
    private final AdminJwtService jwtService;
    private final AdminUserRepository userRepo;

    public AdminAuthController(AuthenticationManager authManager, AdminJwtService jwtService, AdminUserRepository userRepo) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        AdminUser user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new LoginResponse(token, user));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam("token") String token) {
        jwtService.blacklistToken(token);
       
        return ResponseEntity.ok(new ApiResponse(true, "Logged out successfully"));
    }

    // DTO Classes

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private String token;
        private AdminUser user;

        public LoginResponse(String token, AdminUser user) {
            this.token = token;
            this.user = user;
        }

        public String getToken() { return token; }
        public AdminUser getUser() { return user; }
    }

    public static class ApiResponse {
        private boolean success;
        private String message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }
}
