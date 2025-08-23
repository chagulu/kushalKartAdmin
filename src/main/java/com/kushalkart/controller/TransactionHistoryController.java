package com.kushalkart.controller;

import com.kushalkart.dto.TransactionHistoryResponse;
import com.kushalkart.entity.AdminUser;
import com.kushalkart.repository.AdminUserRepository;
import com.kushalkart.service.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/transactions")
public class TransactionHistoryController {

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @GetMapping
    public ResponseEntity<?> getAllTransactionHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            
            // Transaction-specific filters
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long bookingId,
            @RequestParam(required = false) Long balanceTypeId,
            @RequestParam(required = false) Long transactionTypeId,
            @RequestParam(required = false) Long paymentTypeId,
            @RequestParam(required = false) Long transactionStatusId,
            
            // Amount filters
            @RequestParam(required = false) BigDecimal amountMin,
            @RequestParam(required = false) BigDecimal amountMax,
            
            // Date filters
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            
            // Text filters
            @RequestParam(required = false) String reference,
            @RequestParam(required = false) String remarks,
            
            // Sorting
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        AdminUser currentAdmin = getCurrentAdmin();
        if (currentAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Unauthorized"));
        }

        try {
            Page<TransactionHistoryResponse> transactions = transactionHistoryService.getAllTransactionHistory(
                    userId, bookingId, balanceTypeId, transactionTypeId, paymentTypeId, 
                    transactionStatusId, amountMin, amountMax, from, to, reference, remarks,
                    page, size, sortBy, sortDir
            );
            return ResponseEntity.ok(new ApiResponse(true, "Transactions retrieved successfully", transactions));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace(); // For debugging - remove in production
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Something went wrong: " + e.getMessage()));
        }
    }

    // Optional: Get single transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        AdminUser currentAdmin = getCurrentAdmin();
        if (currentAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Unauthorized"));
        }

        try {
            // You can implement this method in service if needed
            // TransactionHistoryResponse transaction = transactionHistoryService.getById(id);
            // return ResponseEntity.ok(new ApiResponse(true, "Transaction retrieved", transaction));
            return ResponseEntity.ok(new ApiResponse(true, "Single transaction endpoint - implement if needed"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Something went wrong"));
        }
    }

    private AdminUser getCurrentAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;
        return adminUserRepository.findByUsername(auth.getName()).orElse(null);
    }

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

        // Getters
        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public Object getData() {
            return data;
        }

        // Setters (optional, for flexibility)
        public void setSuccess(boolean success) {
            this.success = success;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
