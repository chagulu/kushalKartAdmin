package com.kushalkart.repository;

import com.kushalkart.entity.WorkerKyc;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WorkerKycRepository extends JpaRepository<WorkerKyc, Long> {
    Optional<WorkerKyc> findTopByWorkerIdOrderByCreatedAtDesc(Long workerId);
}
