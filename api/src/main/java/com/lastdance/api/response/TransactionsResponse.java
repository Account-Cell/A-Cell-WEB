package com.lastdance.api.response;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsResponse {
    private Long accountHistoryId;
    private String accountId;
    private LocalDateTime transferDate;
    private String transferTarget;
    private BigDecimal transferAmount;
    private BigDecimal remainingAmount;
}
