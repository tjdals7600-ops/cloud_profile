package com.profile.profile.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberCreateRequest {

    private String membername;
    private int age;
    private String mbti;
}
