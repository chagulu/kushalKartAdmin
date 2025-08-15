package com.kushalkart.service;

import com.kushalkart.dto.BookingResponse;
import com.kushalkart.entity.Booking;
import com.kushalkart.repository.BookingRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Page<BookingResponse> getAllBookings(
            String status,
            Long consumerId,
            Long workerId,
            String paymentStatus,
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

        Booking.Status bookingStatus = parseStatus(status);
        Booking.PaymentStatus payStatus = parsePaymentStatus(paymentStatus);

        // Call new repository method (JOIN fetch for names)
        return bookingRepository.findBookingsWithNames(
                bookingStatus, payStatus, consumerId, workerId, pageable
        );
    }

    private Booking.Status parseStatus(String status) {
        if (status == null || status.isBlank()) return null;
        try {
            return Booking.Status.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid booking status: " + Arrays.toString(Booking.Status.values()));
        }
    }

    private Booking.PaymentStatus parsePaymentStatus(String paymentStatus) {
        if (paymentStatus == null || paymentStatus.isBlank()) return null;
        try {
            return Booking.PaymentStatus.valueOf(paymentStatus.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid payment status: " + Arrays.toString(Booking.PaymentStatus.values()));
        }
    }
}
