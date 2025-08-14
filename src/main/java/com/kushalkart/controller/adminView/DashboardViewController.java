package com.kushalkart.controller.adminView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardViewController {

    @GetMapping("/dashboard")
    public String dashboard() {
        // Thymeleaf looks for /templates/dashboard.html
        return "fragments/dashboard";
    }

    @GetMapping("/booking-listing")
    public String bookingListing() {
        return "fragments/bookingListing"; // maps to templates/bookingListing.html
    }
}
