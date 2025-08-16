package com.kushalkart.repository;

import com.kushalkart.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long>, JpaSpecificationExecutor<Worker> {

    Optional<Worker> findByMobile(String mobile);

    Optional<Worker> findByUsername(String username);

    Page<Worker> findByIdInAndServiceId(List<Long> ids, Long serviceId, Pageable pageable);

    Page<Worker> findByIdInAndServiceCategoryId(List<Long> ids, Long serviceCategoryId, Pageable pageable);
}
