package com.lastdance.db.repository;

import com.lastdance.db.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    // Custom queries if needed
}
