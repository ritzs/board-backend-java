package com.example.boardback.controller;

import com.example.boardback.entity.UserEntity;
import com.example.boardback.repository.UserRepository;
import com.example.boardback.service.UserService;
import com.example.boardback.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Resource
    UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> paramMap) {
        String userId = paramMap.get("user_id");
        String userPw = paramMap.get("user_pw");

        UserDetails loginUser = userService.loadUserByUsername(userId); //userId로 정보 가져오기

        Authentication authentication = authenticationManager.authenticate(     //가져온 정보와 입력한 비밀번호로 검증
                new UsernamePasswordAuthenticationToken(loginUser, userPw)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);   // 검증 통과 후 authentication 세팅

        String accessToken = jwtUtil.createToken(loginUser.getUsername(), loginUser.getUsername());     //accessToken 생성

        Map<String, Object> result = new HashMap<>();
        result.put("user_id", loginUser.getUsername());
        result.put("user_token", accessToken);
        result.put("user_role", loginUser.getAuthorities().stream().findFirst().get().getAuthority());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> join (@RequestBody Map<String, String> paramMap) {

        String encPassword = passwordEncoder.encode(paramMap.get("user_password"));
        UserEntity userEntity = UserEntity.builder()
                .userId(paramMap.get("user_id"))
                .userPw(encPassword)
                .userName(paramMap.get("user_name"))
                .build();

        UserEntity savedUser = userRepository.save(userEntity);
        Map<String, Object> result = new HashMap<>();
        result.put("user_idx", savedUser.getIdx());
        result.put("user_id", savedUser.getUserId());
        result.put("user_password", savedUser.getUserPw());
        result.put("user_name", savedUser.getUserName());

        return ResponseEntity.ok(result);
    }
}