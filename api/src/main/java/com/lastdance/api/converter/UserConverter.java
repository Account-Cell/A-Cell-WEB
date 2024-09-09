package com.lastdance.api.converter;

import com.lastdance.db.entity.User;
import com.lastdance.api.dto.UserDTO;

public class UserConverter {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserPw(),
                user.getUserEmail(),
                user.getUserPhonenum(),
                user.getUserName(),
                user.getRegistrationNum(),
                user.getCreateAt(),
                user.getUpdateAt()
        );
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.userId());
        user.setUserPw(userDTO.userPw());
        user.setUserEmail(userDTO.userEmail());
        user.setUserPhonenum(userDTO.userPhonenum());
        user.setUserName(userDTO.userName());
        user.setRegistrationNum(userDTO.registrationNum());
        user.setCreateAt(userDTO.createAt());
        user.setUpdateAt(userDTO.updateAt());
        return user;
    }
}
