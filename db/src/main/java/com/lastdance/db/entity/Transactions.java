package com.lastdance.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {
    @Id
    private Long accountHistoryId;
    private String accountId;
    private LocalDateTime transferDate;
    private String transferTarget;
    private BigDecimal transferAmount;
    private BigDecimal remainingAmount;
}
