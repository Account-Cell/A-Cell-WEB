package com.lastdance.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionsDTO(
        Long accountHistoryId,
        String accountId,
        LocalDateTime transferDate,
        String transferTarget,
        BigDecimal transferAmount,
        BigDecimal remainingAmount
) {}
