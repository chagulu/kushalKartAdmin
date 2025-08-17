package com.kushalkart.controller;

import com.kushalkart.entity.AdminUser;
import com.kushalkart.entity.Worker;
import com.kushalkart.entity.WorkerKyc;
import com.kushalkart.repository.AdminUserRepository;
import com.kushalkart.repository.WorkerKycRepository;
import com.kushalkart.repository.WorkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/worker/kyc")
public class WorkerKycController {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private WorkerKycRepository workerKycRepository;

    /**
     * Submit or update KYC (1 row per worker)
     */
    @PostMapping("/{workerId}")
    @Transactional
    public ResponseEntity<?> uploadKyc(
            @PathVariable Long workerId,
            @RequestParam String aadhaarNumber,
            @RequestParam MultipartFile aadhaarFront,
            @RequestParam MultipartFile aadhaarBack,
            @RequestParam MultipartFile workerPhoto
    ) throws IOException {

        Worker worker = workerRepository.findById(workerId).orElse(null);
        if (worker == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Worker not found"));
        }

        AdminUser currentAdmin = getCurrentAdmin();
        if (currentAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Unauthorized"));
        }

        // 🔹 Fetch existing KYC or create new
        WorkerKyc kyc = workerKycRepository.findById(workerId).orElse(new WorkerKyc());
        kyc.setWorker(worker);
        kyc.setAadhaarNumber(maskAadhaar(aadhaarNumber));

        // File save logic (overwrite each time here; can make conditional if desired)
        if (aadhaarFront != null && !aadhaarFront.isEmpty()) {
            kyc.setAadhaarFrontPath(saveFile(aadhaarFront));
        }
        if (aadhaarBack != null && !aadhaarBack.isEmpty()) {
            kyc.setAadhaarBackPath(saveFile(aadhaarBack));
        }
        if (workerPhoto != null && !workerPhoto.isEmpty()) {
            kyc.setWorkerPhotoPath(saveFile(workerPhoto));
        }

        kyc.setStatus(WorkerKyc.Status.PENDING);
        kyc.setRegisteredBy(currentAdmin.getId());
        kyc.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        if (kyc.getCreatedAt() == null) {
            kyc.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }

        workerKycRepository.save(kyc);

        return ResponseEntity.ok(new ApiResponse(true, "eKYC submitted successfully",
                Map.of("kycId", kyc.getId())));
    }

    /**
     * Approve KYC
     */
    @PostMapping("/{kycId}/approve")
    @Transactional
    public ResponseEntity<?> approveKyc(@PathVariable Long kycId) {
        return updateStatus(kycId, WorkerKyc.Status.VERIFIED);
    }

    /**
     * Reject KYC
     */
    @PostMapping("/{kycId}/reject")
    @Transactional
    public ResponseEntity<?> rejectKyc(@PathVariable Long kycId) {
        return updateStatus(kycId, WorkerKyc.Status.REJECTED);
    }

    /**
     * Get KYC Status
     */
    @GetMapping("/{workerId}/status")
    public ResponseEntity<?> getKycStatus(@PathVariable Long workerId) {
        Optional<WorkerKyc> kycOpt = workerKycRepository.findTopByWorkerIdOrderByCreatedAtDesc(workerId);
        if (kycOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "No KYC record found for worker"));
        }

        WorkerKyc kyc = kycOpt.get();

        return ResponseEntity.ok(new ApiResponse(true, "KYC status retrieved successfully", Map.of(
                "kycId", kyc.getId(),
                "status", kyc.getStatus()
        )));
    }

    // ================= Helpers =================

    private ResponseEntity<?> updateStatus(Long kycId, WorkerKyc.Status status) {
        WorkerKyc kyc = workerKycRepository.findById(kycId).orElse(null);
        if (kyc == null) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "KYC record not found"));
        }

        AdminUser currentAdmin = getCurrentAdmin();
        if (currentAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, "Unauthorized"));
        }

        kyc.setStatus(status);
        kyc.setVerifiedBy(currentAdmin.getId());
        workerKycRepository.save(kyc);

        return ResponseEntity.ok(new ApiResponse(true, "KYC " + status.name()));
    }

    private AdminUser getCurrentAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        String username = auth.getName();
        return adminUserRepository.findByUsername(username).orElse(null);
    }

    private String maskAadhaar(String aadhaar) {
        if (aadhaar == null || aadhaar.length() < 4) return "***";
        String last4 = aadhaar.substring(aadhaar.length() - 4);
        return "**** **** **** " + last4;
    }

    private String saveFile(MultipartFile file) throws IOException {
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new java.io.File("/tmp/" + filename));
        return "/tmp/" + filename; // In production, use a proper storage path
    }

    // Simple response wrapper
    static class ApiResponse {
        private boolean success;
        private String message;
        private Object data;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public ApiResponse(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Object getData() { return data; }
    }
}
