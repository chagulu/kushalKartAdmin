package com.kushalkart.repository;

import com.kushalkart.entity.MasterTransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface MasterTransactionHistoryRepository extends JpaRepository<MasterTransactionHistory, Long>, JpaSpecificationExecutor<MasterTransactionHistory> {

    // Basic finder methods
    Page<MasterTransactionHistory> findByUserId(Long userId, Pageable pageable);
    Page<MasterTransactionHistory> findByBookingId(Long bookingId, Pageable pageable);
    Page<MasterTransactionHistory> findByTransactionTypeId(Long transactionTypeId, Pageable pageable);
    Page<MasterTransactionHistory> findByTransactionStatusId(Long transactionStatusId, Pageable pageable);
    Page<MasterTransactionHistory> findByPaymentTypeId(Long paymentTypeId, Pageable pageable);
    Page<MasterTransactionHistory> findByBalanceTypeId(Long balanceTypeId, Pageable pageable);

    // Amount-based queries
    Page<MasterTransactionHistory> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable);
    Page<MasterTransactionHistory> findByAmountGreaterThanEqual(BigDecimal amount, Pageable pageable);
    Page<MasterTransactionHistory> findByAmountLessThanEqual(BigDecimal amount, Pageable pageable);

    // Date-based queries
    Page<MasterTransactionHistory> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
    Page<MasterTransactionHistory> findByCreatedAtAfter(LocalDateTime from, Pageable pageable);
    Page<MasterTransactionHistory> findByCreatedAtBefore(LocalDateTime to, Pageable pageable);

    // Text-based search
    Page<MasterTransactionHistory> findByTransactionReferenceContainingIgnoreCase(String reference, Pageable pageable);
    Page<MasterTransactionHistory> findByRemarksContainingIgnoreCase(String remarks, Pageable pageable);

    // Combined queries
    Page<MasterTransactionHistory> findByUserIdAndTransactionStatusId(Long userId, Long transactionStatusId, Pageable pageable);
    Page<MasterTransactionHistory> findByBookingIdAndTransactionStatusId(Long bookingId, Long transactionStatusId, Pageable pageable);

    // Custom query with joins for rich data (optional - if you want to join with related tables)
    @Query(
        "SELECT mth FROM MasterTransactionHistory mth " +
        "LEFT JOIN User u ON u.id = mth.userId " +
        "LEFT JOIN Booking b ON b.id = mth.bookingId " +
        "WHERE (:userId IS NULL OR mth.userId = :userId) " +
        "AND (:bookingId IS NULL OR mth.bookingId = :bookingId) " +
        "AND (:transactionTypeId IS NULL OR mth.transactionTypeId = :transactionTypeId) " +
        "AND (:transactionStatusId IS NULL OR mth.transactionStatusId = :transactionStatusId) " +
        "AND (:paymentTypeId IS NULL OR mth.paymentTypeId = :paymentTypeId) " +
        "AND (:balanceTypeId IS NULL OR mth.balanceTypeId = :balanceTypeId) " +
        "AND (:minAmount IS NULL OR mth.amount >= :minAmount) " +
        "AND (:maxAmount IS NULL OR mth.amount <= :maxAmount) " +
        "AND (:fromDate IS NULL OR mth.createdAt >= :fromDate) " +
        "AND (:toDate IS NULL OR mth.createdAt <= :toDate) " +
        "AND (:reference IS NULL OR LOWER(mth.transactionReference) LIKE LOWER(CONCAT('%', :reference, '%'))) " +
        "AND (:remarks IS NULL OR LOWER(mth.remarks) LIKE LOWER(CONCAT('%', :remarks, '%')))"
    )
    Page<MasterTransactionHistory> findTransactionsWithFilters(
        @Param("userId") Long userId,
        @Param("bookingId") Long bookingId,
        @Param("transactionTypeId") Long transactionTypeId,
        @Param("transactionStatusId") Long transactionStatusId,
        @Param("paymentTypeId") Long paymentTypeId,
        @Param("balanceTypeId") Long balanceTypeId,
        @Param("minAmount") BigDecimal minAmount,
        @Param("maxAmount") BigDecimal maxAmount,
        @Param("fromDate") LocalDateTime fromDate,
        @Param("toDate") LocalDateTime toDate,
        @Param("reference") String reference,
        @Param("remarks") String remarks,
        Pageable pageable
    );

    // Summary/Analytics queries (useful for admin dashboards)
    @Query("SELECT COUNT(mth) FROM MasterTransactionHistory mth WHERE mth.transactionStatusId = :statusId")
    Long countByTransactionStatusId(@Param("statusId") Long statusId);

    @Query("SELECT SUM(mth.amount) FROM MasterTransactionHistory mth WHERE mth.transactionStatusId = :statusId")
    BigDecimal sumAmountByTransactionStatusId(@Param("statusId") Long statusId);

    @Query("SELECT COUNT(mth) FROM MasterTransactionHistory mth WHERE mth.createdAt BETWEEN :from AND :to")
    Long countTransactionsBetweenDates(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("SELECT SUM(mth.amount) FROM MasterTransactionHistory mth WHERE mth.createdAt BETWEEN :from AND :to")
    BigDecimal sumAmountBetweenDates(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    // Find transactions by user with date range
    @Query("SELECT mth FROM MasterTransactionHistory mth WHERE mth.userId = :userId AND mth.createdAt BETWEEN :from AND :to")
    Page<MasterTransactionHistory> findUserTransactionsBetweenDates(
        @Param("userId") Long userId, 
        @Param("from") LocalDateTime from, 
        @Param("to") LocalDateTime to, 
        Pageable pageable
    );
}
