package com.leavemanagement.dto;

import com.leavemanagement.entity.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponseDto {
    private Long id;
    private Long userId;
    private String username;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;
    private long leaveDays;
}
