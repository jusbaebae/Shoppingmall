package com.example.shoppingmall.member.controller;

import com.example.shoppingmall.member.dto.*;
import com.example.shoppingmall.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(@Valid @RequestBody MemberSignUpRequest request)
    {
        MemberResponse response = memberService.signUp(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){

        LoginResponse response = memberService.login(request);

        return ResponseEntity.ok(response);
    }

    //토큰 테스트
    @GetMapping("/test")
    public String test(){
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return  authentication.getPrincipal().toString();
    }

    //회원정보조회
    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMyInfo(Authentication authentication){
        Long memberId = Long.valueOf(authentication.getPrincipal().toString());

        MemberResponse response = memberService.getMember(memberId);

        return ResponseEntity.ok(response);
    }
}
