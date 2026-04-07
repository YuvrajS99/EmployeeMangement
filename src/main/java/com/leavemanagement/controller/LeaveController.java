package com.leavemanagement.controller;

import com.leavemanagement.dto.LeaveRequestDto;
import com.leavemanagement.dto.LeaveResponseDto;
import com.leavemanagement.entity.LeaveStatus;
import com.leavemanagement.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;


    @PostMapping("/leave")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<LeaveResponseDto> requestLeave(
            @RequestBody LeaveRequestDto requestDto,
            Authentication authentication) {
        return ResponseEntity.ok(leaveService.requestLeave(authentication.getName(), requestDto));
    }


    @GetMapping("/leave/my")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<LeaveResponseDto>> getMyLeaves(Authentication authentication) {
        return ResponseEntity.ok(leaveService.getMyLeaves(authentication.getName()));
    }


    @GetMapping("/leaves/pending")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<LeaveResponseDto>> getPendingLeaves() {
        return ResponseEntity.ok(leaveService.getPendingLeaves());
    }


    @PutMapping("/leave/{id}/status")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<LeaveResponseDto> updateLeaveStatus(
            @PathVariable Long id,
            @RequestParam LeaveStatus status) {
        return ResponseEntity.ok(leaveService.updateLeaveStatus(id, status));
    }
}
