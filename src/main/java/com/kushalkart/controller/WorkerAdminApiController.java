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

    // GET /admin/workers?status=ACTIVE&aadhaarNumber=...&mobile=...&name=...&from=...&to=...&page=0&size=10
    @GetMapping
    public Page<Worker> getWorkers(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String aadhaarNumber,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return workerService.findWorkersForAdminPanel(
            status, aadhaarNumber, mobile, name, from, to, pageable
        );
    }
}
