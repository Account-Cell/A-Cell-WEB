package com.lastdance.api.dto;

import java.time.LocalDateTime;

public record UserDTO(
        String userId,
        String userPw,
        String userEmail,
        String userPhonenum,
        String userName,
        String registrationNum,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {}
