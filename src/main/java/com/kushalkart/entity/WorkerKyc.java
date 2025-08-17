package com.kushalkart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "worker_kyc")
public class WorkerKyc {

    // If using shared primary key: worker_id is PK and FK
    // Shared primary key with Worker: worker_id is both PK and FK
@Id
@Column(name = "worker_id")
private Long id;

@JsonBackReference
@OneToOne(fetch = FetchType.LAZY)
@MapsId
@JoinColumn(name = "worker_id")
private Worker worker;

@Column(name = "aadhaar_number", nullable = false)
private String aadhaarNumber;

@Column(name = "aadhaar_front_path", nullable = false)
private String aadhaarFrontPath;

@Column(name = "aadhaar_back_path", nullable = false)
private String aadhaarBackPath;

@Column(name = "worker_photo_path", nullable = false)
private String workerPhotoPath;

@Enumerated(EnumType.STRING)
@Column(name = "status")
private Status status = Status.PENDING;

// Store admin IDs (not AdminUser objects). Controller must set currentAdmin.getId().
@Column(name = "registered_by")
private Long registeredBy;

@Column(name = "created_at")
private Timestamp createdAt;

@Column(name = "updated_at")
private Timestamp updatedAt;

@Column(name = "verified_by")
private Long verifiedBy;

public enum Status {
    PENDING, VERIFIED, REJECTED
}

// Getters and setters

public Long getId() { return id; }
public void setId(Long id) { this.id = id; }

public Worker getWorker() { return worker; }
public void setWorker(Worker worker) { this.worker = worker; }

public String getAadhaarNumber() { return aadhaarNumber; }
public void setAadhaarNumber(String aadhaarNumber) { this.aadhaarNumber = aadhaarNumber; }

public String getAadhaarFrontPath() { return aadhaarFrontPath; }
public void setAadhaarFrontPath(String aadhaarFrontPath) { this.aadhaarFrontPath = aadhaarFrontPath; }

public String getAadhaarBackPath() { return aadhaarBackPath; }
public void setAadhaarBackPath(String aadhaarBackPath) { this.aadhaarBackPath = aadhaarBackPath; }

public String getWorkerPhotoPath() { return workerPhotoPath; }
public void setWorkerPhotoPath(String workerPhotoPath) { this.workerPhotoPath = workerPhotoPath; }

public Status getStatus() { return status; }
public void setStatus(Status status) { this.status = status; }

public Long getRegisteredBy() { return registeredBy; }
public void setRegisteredBy(Long registeredBy) { this.registeredBy = registeredBy; }

public Timestamp getCreatedAt() { return createdAt; }
public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

public Timestamp getUpdatedAt() { return updatedAt; }
public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

public Long getVerifiedBy() { return verifiedBy; }
public void setVerifiedBy(Long verifiedBy) { this.verifiedBy = verifiedBy; }

}
