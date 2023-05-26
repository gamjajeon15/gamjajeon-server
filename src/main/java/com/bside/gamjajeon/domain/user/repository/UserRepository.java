package com.bside.gamjajeon.domain.user.repository;

import com.bside.gamjajeon.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findUserByEmail(String email);
}
