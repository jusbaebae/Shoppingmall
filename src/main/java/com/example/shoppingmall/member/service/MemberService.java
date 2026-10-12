package com.example.shoppingmall.member.service;

import com.example.shoppingmall.global.security.jwt.JwtTokenProvider;
import com.example.shoppingmall.member.dto.LoginRequest;
import com.example.shoppingmall.member.dto.LoginResponse;
import com.example.shoppingmall.member.dto.MemberResponse;
import com.example.shoppingmall.member.dto.MemberSignUpRequest;
import com.example.shoppingmall.member.entity.Member;
import com.example.shoppingmall.member.entity.Role;
import com.example.shoppingmall.global.exception.member.DuplicateEmailException;
import com.example.shoppingmall.global.exception.member.InvalidLoginException;
import com.example.shoppingmall.global.exception.member.MemberNotFoundException;
import com.example.shoppingmall.global.exception.member.PasswordMismatchException;
import com.example.shoppingmall.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberResponse signUp(MemberSignUpRequest request){
        if(memberRepository.existsByEmail(request.getEmail())){
            throw new DuplicateEmailException();
        }

        if(!Objects.equals(request.getPassword(), request.getConfirmPassword())){
            throw new PasswordMismatchException();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Member member = Member.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .name(request.getName())
                .phone(request.getPhone())
                .role(Role.USER)
                .build();

        Member savedMember = memberRepository.save(member);

        return MemberResponse.builder()
                .id(savedMember.getId())
                .email(savedMember.getEmail())
                .name(savedMember.getName())
                .nickname(savedMember.getNickname())
                .build();
    }

    public LoginResponse login(LoginRequest request){
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(InvalidLoginException::new);

        if(!passwordEncoder.matches(
                request.getPassword(),
                member.getPassword()
        )){
            throw new InvalidLoginException();
        }

        String token = jwtTokenProvider.createToken(member.getId(), member.getRole());

        return new LoginResponse(token);
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(Long id){
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);

        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhone(),
                member.getNickname(),
                member.getRole()
        );
    }
}
