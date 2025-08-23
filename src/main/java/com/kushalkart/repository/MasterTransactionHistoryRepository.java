package com.kushalkart.repository;

import com.kushalkart.dto.TransactionHistoryResponse;
import com.kushalkart.entity.MasterTransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface MasterTransactionHistoryRepository extends JpaRepository<MasterTransactionHistory, Long>, JpaSpecificationExecutor<MasterTransactionHistory> {

    @Query(
        "SELECT new com.kushalkart.dto.TransactionHistoryResponse(" +
        "mth.id, " +
        "mth.userId, " +
        "u.name, " +
        "u.mobile, " +
        "u.email, " +
        "mth.bookingId, " +
        "mth.balanceTypeId, " +
        "bt.name, " +
        "bt.description, " +
        "mth.transactionTypeId, " +
        "tt.name, " +
        "mth.paymentTypeId, " +
        "pt.name, " +
        "mth.amount, " +
        "mth.remarks, " +
        "mth.transactionReference, " +
        "mth.transactionStatusId, " +
        "ts.name, " +
        "mth.createdAt" +
        ") " +
        "FROM MasterTransactionHistory mth " +
        "LEFT JOIN User u ON u.id = mth.userId " +
        "LEFT JOIN BalanceType bt ON bt.id = mth.balanceTypeId " +
        "LEFT JOIN TransactionType tt ON tt.id = mth.transactionTypeId " +
        "LEFT JOIN PaymentType pt ON pt.id = mth.paymentTypeId " +
        "LEFT JOIN TransactionStatus ts ON ts.id = mth.transactionStatusId " +
        "WHERE (:userId IS NULL OR mth.userId = :userId) " +
        "AND (:bookingId IS NULL OR mth.bookingId = :bookingId) " +
        "AND (:balanceTypeId IS NULL OR mth.balanceTypeId = :balanceTypeId) " +
        "AND (:transactionTypeId IS NULL OR mth.transactionTypeId = :transactionTypeId) " +
        "AND (:paymentTypeId IS NULL OR mth.paymentTypeId = :paymentTypeId) " +
        "AND (:transactionStatusId IS NULL OR mth.transactionStatusId = :transactionStatusId) " +
        "AND (:minAmount IS NULL OR mth.amount >= :minAmount) " +
        "AND (:maxAmount IS NULL OR mth.amount <= :maxAmount) " +
        "AND (:fromDate IS NULL OR mth.createdAt >= :fromDate) " +
        "AND (:toDate IS NULL OR mth.createdAt <= :toDate) " +
        "AND (:reference IS NULL OR LOWER(mth.transactionReference) LIKE LOWER(CONCAT('%', :reference, '%'))) " +
        "AND (:remarks IS NULL OR LOWER(mth.remarks) LIKE LOWER(CONCAT('%', :remarks, '%')))"
    )
    Page<TransactionHistoryResponse> findTransactionsWithDetails(
        @Param("userId") Long userId,
        @Param("bookingId") Long bookingId,
        @Param("balanceTypeId") Long balanceTypeId,
        @Param("transactionTypeId") Long transactionTypeId,
        @Param("paymentTypeId") Long paymentTypeId,
        @Param("transactionStatusId") Long transactionStatusId,
        @Param("minAmount") BigDecimal minAmount,
        @Param("maxAmount") BigDecimal maxAmount,
        @Param("fromDate") LocalDateTime fromDate,
        @Param("toDate") LocalDateTime toDate,
        @Param("reference") String reference,
        @Param("remarks") String remarks,
        Pageable pageable
    );
}
