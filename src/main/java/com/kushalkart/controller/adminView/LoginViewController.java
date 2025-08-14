package com.kushalkart.controller.adminView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {

    @GetMapping("/admin/login")
    public String showLoginPage() {
        // Return the login.html Thymeleaf template (or static login page)
       return "fragments/login"; // name of your login view template file without .html
    }
}
