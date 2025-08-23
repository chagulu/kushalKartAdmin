package com.kushalkart.service;

import com.kushalkart.entity.Worker;
import com.kushalkart.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Join;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

   @Autowired
private WorkerRepository workerRepository;

/**
 * Finds workers for admin panel with optional filters and pagination.
 */
public Page<Worker> findWorkersForAdminPanel(
        String kycStatus,
        String aadhaarNumber,
        String mobile,
        String name,
        LocalDate from,
        LocalDate to,
        Long id,
        Pageable pageable
) {
    Specification<Worker> spec = (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<>();

        if (kycStatus != null && !kycStatus.isEmpty()) {
            predicates.add(cb.equal(root.get("kycStatus"), Worker.KycStatus.valueOf(kycStatus)));
        }

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }

        if (mobile != null && !mobile.isEmpty()) {
            predicates.add(cb.equal(root.get("mobile"), mobile));
        }

        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (from != null) {
            Date fromDate = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
        }

        if (to != null) {
            Date toDate = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());
            predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), toDate));
        }

        // Join KYC only if filtering by Aadhaar number
        if (aadhaarNumber != null && !aadhaarNumber.isEmpty()) {
            // "kyc" must be the exact name of the OneToOne field in Worker
            Join<Object, Object> kycJoin = root.join("kyc", JoinType.INNER);
            predicates.add(cb.equal(kycJoin.get("aadhaarNumber"), aadhaarNumber));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    };

    // Ensure default ordering by createdAt desc if no sort provided in pageable
    if (pageable == null || !pageable.getSort().isSorted()) {
        pageable = org.springframework.data.domain.PageRequest.of(
            (pageable == null ? 0 : pageable.getPageNumber()),
            (pageable == null ? 20 : pageable.getPageSize()),
            org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "createdAt")
        );
    }
    return workerRepository.findAll(spec, pageable);
}

/**
 * Find Worker by ID. Returns null if not found.
 */
public Worker findById(Long id) {
    Optional<Worker> workerOpt = workerRepository.findById(id);
    return workerOpt.orElse(null);
}

}
