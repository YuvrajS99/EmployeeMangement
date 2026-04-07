package com.leavemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveReportDto {
    private Long employeeId;
    private String username;
    private long totalLeaves;
    private long approvedLeaves;
    private long pendingLeaves;
    private long rejectedLeaves;
    // Uses HolidayDto instead of the Holiday entity to avoid entity exposure
    private List<HolidayDto> holidays;
}
