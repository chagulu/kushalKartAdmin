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
    // Serve the KYC registration form page for worker with given ID
    @GetMapping("/worker/kyc")
    public String workerKycForm() {
        // Thymeleaf (or your template engine) loads templates/workerKycForm.html
        return "fragments/workerKycRegistration"; 
    }

    // Serve the KYC registration form page for worker with given ID
    @GetMapping("/worker/listing")
    public String workerListing() {
        // Thymeleaf (or your template engine) loads templates/workerKycForm.html
        return "fragments/workerListing"; 
    }
}
