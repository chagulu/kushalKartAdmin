package com.kushalkart.repository;

import com.kushalkart.dto.BookingResponse;
import com.kushalkart.entity.Booking;
import com.kushalkart.entity.Booking.Status;
import com.kushalkart.entity.Booking.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findByStatus(Status status, Pageable pageable);
    Page<Booking> findByConsumerId(Long consumerId, Pageable pageable);
    Page<Booking> findByWorkerId(Long workerId, Pageable pageable);
    Page<Booking> findByConsumerIdAndWorkerId(Long consumerId, Long workerId, Pageable pageable);
    Page<Booking> findByConsumerIdAndStatus(Long consumerId, Status status, Pageable pageable);
    Page<Booking> findByWorkerIdAndStatus(Long workerId, Status status, Pageable pageable);
    Page<Booking> findByConsumerIdAndWorkerIdAndStatus(Long consumerId, Long workerId, Status status, Pageable pageable);
    Page<Booking> findByPaymentStatus(PaymentStatus paymentStatus, Pageable pageable);

    @Query(
        "SELECT new com.kushalkart.dto.BookingResponse(" +
        "b.id, " +
        "b.consumerName, " +
        "w.name, " +
        "b.serviceId, " +
        "b.service, " +
        "b.description, " +
        "b.scheduledTime, " +
        "b.status, " +
        "b.paymentStatus, " +
        "b.amount, " +
        "b.consumerPhone, " +
        "b.consumerEmail, " +
        "b.address, " +
        "b.bookingMode, " +
        "b.createdAt, " +
        "b.updatedAt" +
        ") " +
        "FROM Booking b " +
        "JOIN Worker w ON w.id = b.workerId " +
        "WHERE (:status IS NULL OR b.status = :status) " +
        "AND (:payStatus IS NULL OR b.paymentStatus = :payStatus) " +
        "AND (:consumerId IS NULL OR b.consumerId = :consumerId) " +
        "AND (:workerId IS NULL OR b.workerId = :workerId) " +
        "AND (:consumerName IS NULL OR LOWER(b.consumerName) LIKE LOWER(CONCAT('%', :consumerName, '%'))) " +
        "AND (:workerName IS NULL OR LOWER(w.name) LIKE LOWER(CONCAT('%', :workerName, '%')))"
    )
    Page<BookingResponse> findBookingsWithNames(
        Status status,
        PaymentStatus payStatus,
        Long consumerId,
        Long workerId,
        String consumerName,
        String workerName,
        Pageable pageable
    );
}
