package com.leavemanagement.service;

import com.leavemanagement.dto.LeaveRequestDto;
import com.leavemanagement.dto.LeaveResponseDto;
import com.leavemanagement.entity.Holiday;
import com.leavemanagement.entity.LeaveRequest;
import com.leavemanagement.entity.LeaveStatus;
import com.leavemanagement.entity.User;
import com.leavemanagement.repository.HolidayRepository;
import com.leavemanagement.repository.LeaveRequestRepository;
import com.leavemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LeaveService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;
    private final HolidayRepository holidayRepository;

    /** EMPLOYEE: apply for leave with holiday-aware day count */
    public LeaveResponseDto requestLeave(String username, LeaveRequestDto requestDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        LocalDate startDate = requestDto.getStartDate();
        LocalDate endDate   = requestDto.getEndDate();

        // leaveDays = totalDays − public holidays in range
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        List<Holiday> holidays = holidayRepository.findHolidaysBetween(startDate, endDate);
        long leaveDays = totalDays - holidays.size();

        LeaveRequest saved = leaveRequestRepository.save(
                LeaveRequest.builder()
                        .user(user)
                        .startDate(startDate)
                        .endDate(endDate)
                        .leaveDays(leaveDays)
                        .status(LeaveStatus.PENDING)
                        .build()
        );
        return toDto(saved);
    }

    /** EMPLOYEE: view own leave history */
    @Transactional(readOnly = true)
    public List<LeaveResponseDto> getMyLeaves(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        return leaveRequestRepository.findByUserId(user.getId())
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    /** MANAGER: view all pending leave requests */
    @Transactional(readOnly = true)
    public List<LeaveResponseDto> getPendingLeaves() {
        return leaveRequestRepository.findByStatus(LeaveStatus.PENDING)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    /** MANAGER: approve or reject a leave request */
    public LeaveResponseDto updateLeaveStatus(Long leaveId, LeaveStatus status) {
        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave Request not found: " + leaveId));
        leave.setStatus(status);
        return toDto(leaveRequestRepository.save(leave));
    }

    // ─── Mapper ──────────────────────────────────────────────────────────────

    private LeaveResponseDto toDto(LeaveRequest leave) {
        return LeaveResponseDto.builder()
                .id(leave.getId())
                .userId(leave.getUser().getId())
                .username(leave.getUser().getUsername())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .status(leave.getStatus())
                .leaveDays(leave.getLeaveDays())
                .build();
    }
}
