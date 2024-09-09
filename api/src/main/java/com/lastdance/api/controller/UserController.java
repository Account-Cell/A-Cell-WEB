package com.lastdance.api.controller;

import com.lastdance.api.dto.UserDTO;
import com.lastdance.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lastdance.api.dto.SignupRequestDTO;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        try {
            userService.registerUser(signupRequestDTO);
            return ResponseEntity.ok("회원가입 성공");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("회원가입 실패: " + e.getMessage());
        }
    }

}
