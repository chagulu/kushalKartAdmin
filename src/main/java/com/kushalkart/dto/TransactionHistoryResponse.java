package com.kushalkart.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionHistoryResponse {
    private Long id;
    private Long userId;
    private Long bookingId;
    private Long balanceTypeId;
    private Long transactionTypeId;
    private Long paymentTypeId;
    private BigDecimal amount;
    private String remarks;
    private String transactionReference;
    private Long transactionStatusId;
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getBalanceTypeId() { return balanceTypeId; }
    public void setBalanceTypeId(Long balanceTypeId) { this.balanceTypeId = balanceTypeId; }

    public Long getTransactionTypeId() { return transactionTypeId; }
    public void setTransactionTypeId(Long transactionTypeId) { this.transactionTypeId = transactionTypeId; }

    public Long getPaymentTypeId() { return paymentTypeId; }
    public void setPaymentTypeId(Long paymentTypeId) { this.paymentTypeId = paymentTypeId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public Long getTransactionStatusId() { return transactionStatusId; }
    public void setTransactionStatusId(Long transactionStatusId) { this.transactionStatusId = transactionStatusId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
