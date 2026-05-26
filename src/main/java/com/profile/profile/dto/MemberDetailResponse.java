package com.profile.profile.dto;

import lombok.Getter;

@Getter
public class MemberDetailResponse {

    private Long id;
    private String membername;
    private int age;
    private String mbti;


    public MemberDetailResponse(Long id, String membername, int age, String mbti) {
        this.id = id;
        this.membername = membername;
        this.age = age;
        this.mbti = mbti;
    }
}
