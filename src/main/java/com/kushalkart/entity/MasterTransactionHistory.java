package com.kushalkart.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "master_transaction_history")
public class MasterTransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // raw FK fields
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "balance_type_id", nullable = false)
    private Long balanceTypeId;

    @Column(name = "transaction_type_id", nullable = false)
    private Long transactionTypeId;

    @Column(name = "payment_type_id")
    private Long paymentTypeId;

    @Column(name = "transaction_status_id", nullable = false)
    private Long transactionStatusId;

    // relationship mappings
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", insertable = false, updatable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "balance_type_id", insertable = false, updatable = false)
    private BalanceType balanceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_type_id", insertable = false, updatable = false)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_id", insertable = false, updatable = false)
    private PaymentType paymentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_status_id", insertable = false, updatable = false)
    private TransactionStatus transactionStatus;

    // other columns
    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "transaction_reference")
    private String transactionReference;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    // getters and setters for raw fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getBalanceTypeId() {
        return balanceTypeId;
    }

    public void setBalanceTypeId(Long balanceTypeId) {
        this.balanceTypeId = balanceTypeId;
    }

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public Long getTransactionStatusId() {
        return transactionStatusId;
    }

    public void setTransactionStatusId(Long transactionStatusId) {
        this.transactionStatusId = transactionStatusId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // getters for relationships

    public User getUser() {
        return user;
    }

    public Booking getBooking() {
        return booking;
    }

    public BalanceType getBalanceType() {
        return balanceType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }
}
