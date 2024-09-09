package com.lastdance.api.service;

import com.lastdance.api.dto.SignupRequestDTO;
import com.lastdance.api.dto.UserDTO;
import com.lastdance.db.entity.User;
import com.lastdance.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.lastdance.api.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getUserPw(),
                new ArrayList<>()
        );
    }

    public UserDTO getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
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

    public void registerUser(SignupRequestDTO signupRequest) {
        User user = new User();
        user.setUserId(signupRequest.getEmail()); // 이메일을 사용자 ID로 사용
        user.setUserPw(passwordEncoder.encode(signupRequest.getPassword())); // 비밀번호 암호화
        user.setUserEmail(signupRequest.getEmail());
        user.setUserPhonenum(signupRequest.getPhoneNumber());
        user.setUserName(signupRequest.getName());
        user.setRegistrationNum(generateRegistrationNum()); // 등록 번호 생성
        user.setCreateAt(LocalDateTime.now());
        user.setUpdateAt(LocalDateTime.now());

        userRepository.save(user); // 사용자 저장
    }

    public String loginUser(String email, String password) {
        User user = userRepository.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getUserPw())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getUserId()); // JWT 토큰 생성 후 반환
    }

    private String generateRegistrationNum() {
        Random random = new Random();
        int randomNum = random.nextInt(1000); // 0 ~ 999 사이의 랜덤 숫자
        long timestamp = System.currentTimeMillis();
        return "REG" + timestamp + String.format("%03d", randomNum); // 현재 시간 + 3자리 랜덤 숫자
    }
}
