package com.example.shoppingmall.member.dto;

import com.example.shoppingmall.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponse {
    private long id;
    private String email;
    private String name;
    private String phone;
    private String nickname;
    private Role role;
}
