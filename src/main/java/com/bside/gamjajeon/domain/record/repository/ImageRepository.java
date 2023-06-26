package com.bside.gamjajeon.domain.record.repository;

import com.bside.gamjajeon.domain.record.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
