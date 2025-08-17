package com.kushalkart.repository;

import com.kushalkart.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long>, JpaSpecificationExecutor<Worker> {

    Optional<Worker> findByMobile(String mobile);

    Optional<Worker> findByUsername(String username);

    Page<Worker> findByIdInAndServiceId(List<Long> ids, Long serviceId, Pageable pageable);

    Page<Worker> findByIdInAndServiceCategoryId(List<Long> ids, Long serviceCategoryId, Pageable pageable);

    // Optional helper: fetch workers by Aadhaar number through the OneToOne 'kyc' relation
    // Requires Worker to have: @OneToOne(mappedBy="worker") private WorkerKyc kyc;
    @Query("SELECT w FROM Worker w JOIN w.kyc k WHERE k.aadhaarNumber = :aadhaarNumber")
    Page<Worker> findByKycAadhaarNumber(@Param("aadhaarNumber") String aadhaarNumber, Pageable pageable);

    // Optional helper: fetch a single worker with its KYC eagerly to avoid N+1 when you need images/kyc fields
    @EntityGraph(attributePaths = {"kyc"})
    @Query("SELECT w FROM Worker w WHERE w.id = :id")
    Optional<Worker> findByIdWithKyc(@Param("id") Long id);


}
