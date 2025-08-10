package com.kushalkart.repository;

import com.kushalkart.entity.Booking;
import com.kushalkart.entity.Booking.Status;
import com.kushalkart.entity.Booking.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findByStatus(Status status, Pageable pageable);
    Page<Booking> findByConsumerId(Long consumerId, Pageable pageable);
    Page<Booking> findByWorkerId(Long workerId, Pageable pageable);
    Page<Booking> findByConsumerIdAndWorkerId(Long consumerId, Long workerId, Pageable pageable);
    Page<Booking> findByConsumerIdAndStatus(Long consumerId, Status status, Pageable pageable);
    Page<Booking> findByWorkerIdAndStatus(Long workerId, Status status, Pageable pageable);
    Page<Booking> findByConsumerIdAndWorkerIdAndStatus(Long consumerId, Long workerId, Status status, Pageable pageable);
    Page<Booking> findByPaymentStatus(PaymentStatus paymentStatus, Pageable pageable);
}
