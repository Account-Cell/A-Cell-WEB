package com.lastdance.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDTO(
        String accountId,
        String userId,
        String accountPw,
        BigDecimal balance,
        LocalDateTime createAt
) {}
