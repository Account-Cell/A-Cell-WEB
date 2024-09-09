package com.lastdance.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAccountDTO {

    @NotBlank
    private String userId; // 사용자 ID

    @NotBlank
    private String accountPassword; // 계좌 비밀번호
}
