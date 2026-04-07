package com.leavemanagement.controller;

import com.leavemanagement.dto.LeaveReportDto;
import com.leavemanagement.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


    @GetMapping("/employee/{id}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<LeaveReportDto> getEmployeeReport(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getEmployeeReport(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<List<LeaveReportDto>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }
}
