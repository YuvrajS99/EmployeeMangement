package com.leavemanagement.service;

import com.leavemanagement.dto.HolidayDto;
import com.leavemanagement.dto.LeaveReportDto;
import com.leavemanagement.entity.LeaveRequest;
import com.leavemanagement.entity.LeaveStatus;
import com.leavemanagement.entity.User;
import com.leavemanagement.repository.HolidayRepository;
import com.leavemanagement.repository.LeaveRequestRepository;
import com.leavemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final UserRepository userRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final HolidayRepository holidayRepository;

    /** HR: report for a single employee */
    public LeaveReportDto getEmployeeReport(Long employeeId) {
        User user = userRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));
        return buildReport(user);
    }

    /** HR: report for all employees */
    public List<LeaveReportDto> getAllReports() {
        return userRepository.findAll()
                .stream()
                .map(this::buildReport)
                .collect(Collectors.toList());
    }

    private LeaveReportDto buildReport(User user) {
        List<LeaveRequest> requests = leaveRequestRepository.findByUserId(user.getId());

        long totalLeaves    = requests.size();
        long approvedLeaves = requests.stream().filter(r -> r.getStatus() == LeaveStatus.APPROVED).count();
        long pendingLeaves  = requests.stream().filter(r -> r.getStatus() == LeaveStatus.PENDING).count();
        long rejectedLeaves = requests.stream().filter(r -> r.getStatus() == LeaveStatus.REJECTED).count();

        List<HolidayDto> holidays = holidayRepository.findAll()
                .stream()
                .map(h -> HolidayDto.builder()
                        .id(h.getId())
                        .holidayDate(h.getHolidayDate())
                        .holidayName(h.getHolidayName())
                        .build())
                .collect(Collectors.toList());

        return LeaveReportDto.builder()
                .employeeId(user.getId())
                .username(user.getUsername())
                .totalLeaves(totalLeaves)
                .approvedLeaves(approvedLeaves)
                .pendingLeaves(pendingLeaves)
                .rejectedLeaves(rejectedLeaves)
                .holidays(holidays)
                .build();
    }
}
