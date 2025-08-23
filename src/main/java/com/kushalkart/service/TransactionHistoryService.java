package com.kushalkart.service;

import com.kushalkart.dto.TransactionHistoryResponse;
import com.kushalkart.entity.MasterTransactionHistory;
import com.kushalkart.repository.MasterTransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionHistoryService {

    @Autowired
    private MasterTransactionHistoryRepository transactionHistoryRepository;

    public Page<TransactionHistoryResponse> getAllTransactionHistory(
            Long userId,
            Long bookingId,
            Long balanceTypeId,
            Long transactionTypeId,
            Long paymentTypeId,
            Long transactionStatusId,
            BigDecimal amountMin,
            BigDecimal amountMax,
            LocalDateTime from,
            LocalDateTime to,
            String reference,
            String remarks,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.max(size, 1),
                Sort.by("asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy)
        );

        // Build specifications for filtering
        Specification<MasterTransactionHistory> spec = Specification
                .where(userIdEquals(userId))
                .and(bookingIdEquals(bookingId))
                .and(balanceTypeIdEquals(balanceTypeId))
                .and(transactionTypeIdEquals(transactionTypeId))
                .and(paymentTypeIdEquals(paymentTypeId))
                .and(transactionStatusIdEquals(transactionStatusId))
                .and(amountBetween(amountMin, amountMax))
                .and(createdAtBetween(from, to))
                .and(referenceLike(reference))
                .and(remarksLike(remarks));

        Page<MasterTransactionHistory> transactionPage = transactionHistoryRepository.findAll(spec, pageable);
        
        // Convert to DTO
        return transactionPage.map(this::convertToDto);
    }

    private TransactionHistoryResponse convertToDto(MasterTransactionHistory transaction) {
        TransactionHistoryResponse response = new TransactionHistoryResponse();
        response.setId(transaction.getId());
        response.setUserId(transaction.getUserId());
        response.setBookingId(transaction.getBookingId());
        response.setBalanceTypeId(transaction.getBalanceTypeId());
        response.setTransactionTypeId(transaction.getTransactionTypeId());
        response.setPaymentTypeId(transaction.getPaymentTypeId());
        response.setAmount(transaction.getAmount());
        response.setRemarks(transaction.getRemarks());
        response.setTransactionReference(transaction.getTransactionReference());
        response.setTransactionStatusId(transaction.getTransactionStatusId());
        response.setCreatedAt(transaction.getCreatedAt());
        return response;
    }

    // Specification methods for filtering
    private Specification<MasterTransactionHistory> userIdEquals(Long userId) {
        return (root, query, criteriaBuilder) -> 
            userId == null ? null : criteriaBuilder.equal(root.get("userId"), userId);
    }

    private Specification<MasterTransactionHistory> bookingIdEquals(Long bookingId) {
        return (root, query, criteriaBuilder) -> 
            bookingId == null ? null : criteriaBuilder.equal(root.get("bookingId"), bookingId);
    }

    private Specification<MasterTransactionHistory> balanceTypeIdEquals(Long balanceTypeId) {
        return (root, query, criteriaBuilder) -> 
            balanceTypeId == null ? null : criteriaBuilder.equal(root.get("balanceTypeId"), balanceTypeId);
    }

    private Specification<MasterTransactionHistory> transactionTypeIdEquals(Long transactionTypeId) {
        return (root, query, criteriaBuilder) -> 
            transactionTypeId == null ? null : criteriaBuilder.equal(root.get("transactionTypeId"), transactionTypeId);
    }

    private Specification<MasterTransactionHistory> paymentTypeIdEquals(Long paymentTypeId) {
        return (root, query, criteriaBuilder) -> 
            paymentTypeId == null ? null : criteriaBuilder.equal(root.get("paymentTypeId"), paymentTypeId);
    }

    private Specification<MasterTransactionHistory> transactionStatusIdEquals(Long transactionStatusId) {
        return (root, query, criteriaBuilder) -> 
            transactionStatusId == null ? null : criteriaBuilder.equal(root.get("transactionStatusId"), transactionStatusId);
    }

    private Specification<MasterTransactionHistory> amountBetween(BigDecimal min, BigDecimal max) {
        return (root, query, criteriaBuilder) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return criteriaBuilder.between(root.get("amount"), min, max);
            if (min != null) return criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), min);
            return criteriaBuilder.lessThanOrEqualTo(root.get("amount"), max);
        };
    }

    private Specification<MasterTransactionHistory> createdAtBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, criteriaBuilder) -> {
            if (from == null && to == null) return null;
            if (from != null && to != null) return criteriaBuilder.between(root.get("createdAt"), from, to);
            if (from != null) return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), from);
            return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }

    private Specification<MasterTransactionHistory> referenceLike(String reference) {
        return (root, query, criteriaBuilder) -> 
            (reference == null || reference.isBlank()) ? null : 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("transactionReference")), 
                               "%" + reference.toLowerCase() + "%");
    }

    private Specification<MasterTransactionHistory> remarksLike(String remarks) {
        return (root, query, criteriaBuilder) -> 
            (remarks == null || remarks.isBlank()) ? null : 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("remarks")), 
                               "%" + remarks.toLowerCase() + "%");
    }
}
