package com.kushalkart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kushalkart.entity.Booking;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse {

    private Long id;
    private String consumerName;   // New - human-readable consumer name
    private String workerName;     // New - human-readable worker name
    private Long serviceId;
    private String service;
    private String description;
    private LocalDateTime scheduledTime;
    private Booking.Status status;
    private Booking.PaymentStatus paymentStatus;
    private BigDecimal amount;
    private String consumerPhone;
    private String consumerEmail;
    private String address;
    private String bookingMode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor used by JPQL projection in BookingRepository.findBookingsWithNames
     */
    public BookingResponse(Long id,
                           String consumerName,
                           String workerName,
                           Long serviceId,
                           String service,
                           String description,
                           LocalDateTime scheduledTime,
                           Booking.Status status,
                           Booking.PaymentStatus paymentStatus,
                           BigDecimal amount,
                           String consumerPhone,
                           String consumerEmail,
                           String address,
                           String bookingMode,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
        this.id = id;
        this.consumerName = consumerName;
        this.workerName = workerName;
        this.serviceId = serviceId;
        this.service = service;
        this.description = description;
        this.scheduledTime = scheduledTime;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.consumerPhone = consumerPhone;
        this.consumerEmail = consumerEmail;
        this.address = address;
        this.bookingMode = bookingMode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Constructor if you still want to map from Booking entity directly.
     * (kept for backward compatibility in other parts of the app)
     */
    public BookingResponse(Booking b) {
        this.id = b.getId();
        this.consumerName = b.getConsumerName();
        this.workerName = null; // You would need to set workerName manually here if using this constructor
        this.serviceId = b.getServiceId();
        this.service = b.getService();
        this.description = b.getDescription();
        this.scheduledTime = b.getScheduledTime();
        this.status = b.getStatus();
        this.paymentStatus = b.getPaymentStatus();
        this.amount = b.getAmount();
        this.consumerPhone = b.getConsumerPhone();
        this.consumerEmail = b.getConsumerEmail();
        this.address = b.getAddress();
        this.bookingMode = b.getBookingMode();
        this.createdAt = b.getCreatedAt();
        this.updatedAt = b.getUpdatedAt();
    }

    // ======= Getters for JSON Serialization =======

    public Long getId() {
        return id;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public String getWorkerName() {
        return workerName;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public String getService() {
        return service;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public Booking.Status getStatus() {
        return status;
    }

    public Booking.PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getConsumerPhone() {
        return consumerPhone;
    }

    public String getConsumerEmail() {
        return consumerEmail;
    }

    public String getAddress() {
        return address;
    }

    public String getBookingMode() {
        return bookingMode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
