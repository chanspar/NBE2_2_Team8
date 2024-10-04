package edu.example.learner.member.controller;

import edu.example.learner.member.dto.LoginDTO;
import edu.example.learner.member.dto.MemberDTO;
import edu.example.learner.member.service.CustomUserDetailsService;
import edu.example.learner.member.service.MemberService;
import edu.example.learner.security.util.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/join")
@RequiredArgsConstructor
@Log4j2
public class JoinRestController {
    private final MemberService memberService;
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    //회원가입
    @PostMapping("/register")
    public ResponseEntity<String> memberRegister(@RequestBody @Validated MemberDTO memberDTO) {
        log.info("--- memberRegister()");
        memberService.register(memberDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공하셨습니다.");
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Long>> login(@RequestBody @Validated LoginDTO loginDTO, HttpServletResponse response) throws IOException {
        log.info("--- login()");

        LoginDTO readInfo = memberService.login(loginDTO.getEmail(), loginDTO.getPassword());

        response.addCookie(readInfo.getCookie());

        Map<String, Long> responseBody = new HashMap<>();
        responseBody.put("memberId", readInfo.getMemberId());

        return ResponseEntity.ok(responseBody);
    }
}


