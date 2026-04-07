package com.leavemanagement.repository;

import com.leavemanagement.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {


    @Query("SELECT h FROM Holiday h WHERE h.holidayDate BETWEEN :startDate AND :endDate")
    List<Holiday> findHolidaysBetween(@Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);
}
