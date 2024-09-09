package com.lastdance.db.repository;

import com.lastdance.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    // Custom queries if needed
}
