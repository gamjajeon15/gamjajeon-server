package com.bside.gamjajeon.domain.record.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bside.gamjajeon.domain.record.dto.response.MoodResponse;
import com.bside.gamjajeon.domain.record.entity.Record;
import com.bside.gamjajeon.domain.user.entity.User;

public interface RecordRepository extends JpaRepository<Record, Long> {
    void deleteByUser(User user);

    @Query("SELECT COUNT(CASE r.moodType WHEN  0 THEN 1 END) AS potato, "
        + "COUNT(CASE WHEN r.moodType = 1 THEN 1 END) AS sweetPotato "
        + "FROM Record r WHERE r.user = :user AND YEAR(r.createdAt) = :year")
    MoodResponse findMoodTypeByUserAndYear(User user, Integer year);

    @Query("SELECT COUNT(CASE r.moodType WHEN 0 THEN 1 END) AS potato, COUNT(CASE WHEN r.moodType = 1 THEN 1 END) AS sweetPotato "
        + "FROM Record r WHERE r.user = :user AND r.recordDate >= DATE(:startDate) and r.recordDate <= DATE(:endDate)")
    MoodResponse findMoodTypeCountByUserAndDate(User user, LocalDate startDate, LocalDate endDate);

}
