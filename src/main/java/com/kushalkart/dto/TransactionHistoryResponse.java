package com.kushalkart.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionHistoryResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String userMobile;
    private String userEmail;
    
    private Long bookingId;
    
    private Long balanceTypeId;
    private String balanceTypeName;
    private String balanceTypeDescription;
    
    private Long transactionTypeId;
    private String transactionTypeName;
    
    private Long paymentTypeId;
    private String paymentTypeName;
    
    private BigDecimal amount;
    private String remarks;
    private String transactionReference;
    
    private Long transactionStatusId;
    private String transactionStatusName;
    
    private LocalDateTime createdAt;

    // No-arg constructor
    public TransactionHistoryResponse() {}

    // Constructor matching JPQL projection order
    public TransactionHistoryResponse(
        Long id,
        Long userId,
        String userName,
        String userMobile,
        String userEmail,
        Long bookingId,
        Long balanceTypeId,
        String balanceTypeName,
        String balanceTypeDescription,
        Long transactionTypeId,
        String transactionTypeName,
        Long paymentTypeId,
        String paymentTypeName,
        BigDecimal amount,
        String remarks,
        String transactionReference,
        Long transactionStatusId,
        String transactionStatusName,
        LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userMobile = userMobile;
        this.userEmail = userEmail;
        this.bookingId = bookingId;
        this.balanceTypeId = balanceTypeId;
        this.balanceTypeName = balanceTypeName;
        this.balanceTypeDescription = balanceTypeDescription;
        this.transactionTypeId = transactionTypeId;
        this.transactionTypeName = transactionTypeName;
        this.paymentTypeId = paymentTypeId;
        this.paymentTypeName = paymentTypeName;
        this.amount = amount;
        this.remarks = remarks;
        this.transactionReference = transactionReference;
        this.transactionStatusId = transactionStatusId;
        this.transactionStatusName = transactionStatusName;
        this.createdAt = createdAt;
    }

    // Getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserMobile() { return userMobile; }  
    public void setUserMobile(String userMobile) { this.userMobile = userMobile; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getBalanceTypeId() { return balanceTypeId; }
    public void setBalanceTypeId(Long balanceTypeId) { this.balanceTypeId = balanceTypeId; }

    public String getBalanceTypeName() { return balanceTypeName; }
    public void setBalanceTypeName(String balanceTypeName) { this.balanceTypeName = balanceTypeName; }

    public String getBalanceTypeDescription() { return balanceTypeDescription; }
    public void setBalanceTypeDescription(String balanceTypeDescription) { this.balanceTypeDescription = balanceTypeDescription; }

    public Long getTransactionTypeId() { return transactionTypeId; }
    public void setTransactionTypeId(Long transactionTypeId) { this.transactionTypeId = transactionTypeId; }

    public String getTransactionTypeName() { return transactionTypeName; }
    public void setTransactionTypeName(String transactionTypeName) { this.transactionTypeName = transactionTypeName; }

    public Long getPaymentTypeId() { return paymentTypeId; }
    public void setPaymentTypeId(Long paymentTypeId) { this.paymentTypeId = paymentTypeId; }

    public String getPaymentTypeName() { return paymentTypeName; }
    public void setPaymentTypeName(String paymentTypeName) { this.paymentTypeName = paymentTypeName; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public Long getTransactionStatusId() { return transactionStatusId; }
    public void setTransactionStatusId(Long transactionStatusId) { this.transactionStatusId = transactionStatusId; }

    public String getTransactionStatusName() { return transactionStatusName; }
    public void setTransactionStatusName(String transactionStatusName) { this.transactionStatusName = transactionStatusName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
