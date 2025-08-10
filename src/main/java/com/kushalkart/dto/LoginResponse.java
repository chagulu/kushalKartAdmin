package com.kushalkart.dto;

import com.kushalkart.entity.AdminUser;

public class LoginResponse {
    private String token;
    private AdminUserDto admin;

    public LoginResponse(String token, AdminUser user) {
        this.token = token;
        this.admin = new AdminUserDto(user);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AdminUserDto getAdmin() {
        return admin;
    }

    public void setAdmin(AdminUserDto admin) {
        this.admin = admin;
    }
}
