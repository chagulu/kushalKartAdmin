package com.kushalkart.service;

import com.kushalkart.entity.Worker;
import com.kushalkart.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
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
    public Page<Worker> findWorkersForAdminPanel(String kycStatus, String aadhaarNumber, String mobile,
                                                String name, LocalDate from, LocalDate to, Pageable pageable) {
        Specification<Worker> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (kycStatus != null && !kycStatus.isEmpty()) {
                // kycStatus is an enum
                predicates.add(cb.equal(root.get("kycStatus"), Worker.KycStatus.valueOf(kycStatus)));
            }
            // If aadhaarNumber exists in entity, add this filter; otherwise remove this block
            // if (aadhaarNumber != null && !aadhaarNumber.isEmpty()) {
            //     predicates.add(cb.equal(root.get("aadhaarNumber"), aadhaarNumber));
            // }
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

            return cb.and(predicates.toArray(new Predicate[0]));
        };

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
