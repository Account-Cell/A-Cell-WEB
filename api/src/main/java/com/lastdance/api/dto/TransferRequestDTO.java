package com.lastdance.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransferRequestDTO {

    @NotBlank
    private String fromAccountId; // 송금 계좌 ID

    @NotBlank
    private String toAccountId; // 수신 계좌 ID

    @Positive
    private double amount; // 송금 금액
}
