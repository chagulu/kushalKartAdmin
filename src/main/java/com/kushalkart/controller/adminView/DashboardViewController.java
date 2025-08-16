package com.kushalkart.controller.adminView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
            // Redirect to list if not found
            return "redirect:/admin/workers";
        }
        model.addAttribute("worker", worker);
        return "fragments/workerKycRegistration";
    }

    // Serve the listing page
    @GetMapping("/worker/listing")
    public String workerListing() {
        return "fragments/workerListing";
    }

    // Handle the POST request when updating KYC info
    // @PostMapping("/admin/worker/kyc/update")
    // public String updateWorkerKyc(Worker worker) {
    //     workerService.save(worker);  // Ensure this method saves or updates the Worker entity
    //     return "redirect:/admin/workers";
    // }
}
