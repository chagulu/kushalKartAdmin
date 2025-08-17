package com.kushalkart.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class WorkerModel {

    private Long id;
private Long serviceCategoryId;
private Long serviceId;
private String skills;
private String bio;
private BigDecimal ratePerHour;
private Boolean verified;
private BigDecimal locationLat;
private BigDecimal locationLng;
private Float rating;
private Integer completedJobs;
private Integer creditScore;
private String kycStatus; // PENDING, APPROVED, REJECTED
private Long registeredBy;
private Timestamp createdAt;
private Timestamp updatedAt;
private String name;
private String username;
private String email;
private String mobile;
private String service;

// Image fields newly exposed in the DTO
private String aadhaarFront;
private String aadhaarBack;
private String workerPhoto;

public WorkerModel(com.kushalkart.entity.Worker worker) {
    this.id = worker.getId();
    this.serviceCategoryId = worker.getServiceCategoryId();
    this.serviceId = worker.getServiceId();
    this.skills = worker.getSkills();
    this.bio = worker.getBio();
    this.ratePerHour = worker.getRatePerHour();
    this.verified = worker.isVerified();
    this.locationLat = worker.getLocationLat();
    this.locationLng = worker.getLocationLng();
    this.rating = worker.getRating();
    this.completedJobs = worker.getCompletedJobs();
    this.creditScore = worker.getCreditScore();
    this.kycStatus = worker.getKycStatus() != null ? worker.getKycStatus().name() : null;
    this.registeredBy = worker.getRegisteredBy();
    this.createdAt = worker.getCreatedAt();
    this.updatedAt = worker.getUpdatedAt();
    this.name = worker.getName();
    this.username = worker.getUsername();
    this.email = worker.getEmail();
    this.mobile = worker.getMobile();
    this.service = worker.getService();

    // Map image fields
    this.aadhaarFront = worker.getAadhaarFront();
    this.aadhaarBack = worker.getAadhaarBack();
    this.workerPhoto = worker.getWorkerPhoto();
}

// Getters and setters

public Long getId() { return id; }
public void setId(Long id) { this.id = id; }

public Long getServiceCategoryId() { return serviceCategoryId; }
public void setServiceCategoryId(Long serviceCategoryId) { this.serviceCategoryId = serviceCategoryId; }

public Long getServiceId() { return serviceId; }
public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

public String getSkills() { return skills; }
public void setSkills(String skills) { this.skills = skills; }

public String getBio() { return bio; }
public void setBio(String bio) { this.bio = bio; }

public BigDecimal getRatePerHour() { return ratePerHour; }
public void setRatePerHour(BigDecimal ratePerHour) { this.ratePerHour = ratePerHour; }

public Boolean getVerified() { return verified; }
public void setVerified(Boolean verified) { this.verified = verified; }

public BigDecimal getLocationLat() { return locationLat; }
public void setLocationLat(BigDecimal locationLat) { this.locationLat = locationLat; }

public BigDecimal getLocationLng() { return locationLng; }
public void setLocationLng(BigDecimal locationLng) { this.locationLng = locationLng; }

public Float getRating() { return rating; }
public void setRating(Float rating) { this.rating = rating; }

public Integer getCompletedJobs() { return completedJobs; }
public void setCompletedJobs(Integer completedJobs) { this.completedJobs = completedJobs; }

public Integer getCreditScore() { return creditScore; }
public void setCreditScore(Integer creditScore) { this.creditScore = creditScore; }

public String getKycStatus() { return kycStatus; }
public void setKycStatus(String kycStatus) { this.kycStatus = kycStatus; }

public Long getRegisteredBy() { return registeredBy; }
public void setRegisteredBy(Long registeredBy) { this.registeredBy = registeredBy; }

public Timestamp getCreatedAt() { return createdAt; }
public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

public Timestamp getUpdatedAt() { return updatedAt; }
public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

public String getName() { return name; }
public void setName(String name) { this.name = name; }

public String getUsername() { return username; }
public void setUsername(String username) { this.username = username; }

public String getEmail() { return email; }
public void setEmail(String email) { this.email = email; }

public String getMobile() { return mobile; }
public void setMobile(String mobile) { this.mobile = mobile; }

public String getService() { return service; }
public void setService(String service) { this.service = service; }

public String getAadhaarFront() { return aadhaarFront; }
public void setAadhaarFront(String aadhaarFront) { this.aadhaarFront = aadhaarFront; }

public String getAadhaarBack() { return aadhaarBack; }
public void setAadhaarBack(String aadhaarBack) { this.aadhaarBack = aadhaarBack; }

public String getWorkerPhoto() { return workerPhoto; }
public void setWorkerPhoto(String workerPhoto) { this.workerPhoto = workerPhoto; }

}
