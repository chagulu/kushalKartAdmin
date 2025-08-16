// File: DashboardViewController.java
package com.kushalkart.controller.adminView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kushalkart.entity.Worker;
import com.kushalkart.service.WorkerService;

@Controller
public class DashboardViewController {

    @Autowired
    private WorkerService workerService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "fragments/dashboard";
    }

    @GetMapping("/booking-listing")
    public String bookingListing() {
        return "fragments/bookingListing";
    }

    // Serve the KYC registration form page for worker with given ID
    @GetMapping("/worker/kyc")
    public String workerKycForm(@RequestParam Long id, Model model) {
        Worker worker = workerService.findById(id);
        if (worker == null) {
            return "redirect:/admin/workers";
        }
        model.addAttribute("worker", worker);
        return "fragments/workerKycRegistration";
    }

    @GetMapping("/worker/listing")
    public String workerListing() {
        return "fragments/workerListing";
    }
}
