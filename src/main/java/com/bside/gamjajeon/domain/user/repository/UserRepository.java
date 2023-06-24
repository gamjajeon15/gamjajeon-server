package com.bside.gamjajeon.domain.user.repository;

import com.bside.gamjajeon.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameAndEmail(String username, String email);

    Optional<User> findUserByUsername(String username);

    @Query("SELECT u.username FROM User u where u.email = :email")
    Optional<String> findUsernameByEmail(String email);
}
