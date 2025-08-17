package com.kushalkart.controller;

import com.kushalkart.model.WorkerModel;
import com.kushalkart.entity.Worker;

import com.kushalkart.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/workers")
public class WorkerAdminApiController {

   @Autowired
private WorkerService workerService;

// GET /admin/workers?kycStatus=PENDING&aadhaarNumber=...&mobile=...&name=...&from=...&to=...&id=1&page=0&size=10
@GetMapping
public Page<Worker> getWorkers(
        @RequestParam(required = false, name = "kycStatus") String kycStatus,
        @RequestParam(required = false) String aadhaarNumber,
        @RequestParam(required = false) String mobile,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
        @RequestParam(required = false) Long id,
        @PageableDefault(size = 10) Pageable pageable
) {
    return workerService.findWorkersForAdminPanel(
            kycStatus, aadhaarNumber, mobile, name, from, to, id, pageable
    );
}

}
