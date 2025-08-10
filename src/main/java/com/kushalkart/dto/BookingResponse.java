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
    private Long consumerId;
    private Long workerId;
    private Long serviceId;
    private String service;
    private String description;
    private LocalDateTime scheduledTime;
    private Booking.Status status;
    private Booking.PaymentStatus paymentStatus;
    private BigDecimal amount;
    private String consumerName;
    private String consumerPhone;
    private String consumerEmail;
    private String address;
    private String bookingMode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BookingResponse(Booking b) {
        this.id = b.getId();
        this.consumerId = b.getConsumerId();
        this.workerId = b.getWorkerId();
        this.serviceId = b.getServiceId();
        this.service = b.getService();
        this.description = b.getDescription();
        this.scheduledTime = b.getScheduledTime();
        this.status = b.getStatus();
        this.paymentStatus = b.getPaymentStatus();
        this.amount = b.getAmount();
        this.consumerName = b.getConsumerName();
        this.consumerPhone = b.getConsumerPhone();
        this.consumerEmail = b.getConsumerEmail();
        this.address = b.getAddress();
        this.bookingMode = b.getBookingMode();
        this.createdAt = b.getCreatedAt();
        this.updatedAt = b.getUpdatedAt();
    }

    public Long getId() { return id; }
    public Long getConsumerId() { return consumerId; }
    public Long getWorkerId() { return workerId; }
    public Long getServiceId() { return serviceId; }
    public String getService() { return service; }
    public String getDescription() { return description; }
    public LocalDateTime getScheduledTime() { return scheduledTime; }
    public Booking.Status getStatus() { return status; }
    public Booking.PaymentStatus getPaymentStatus() { return paymentStatus; }
    public BigDecimal getAmount() { return amount; }
    public String getConsumerName() { return consumerName; }
    public String getConsumerPhone() { return consumerPhone; }
    public String getConsumerEmail() { return consumerEmail; }
    public String getAddress() { return address; }
    public String getBookingMode() { return bookingMode; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
