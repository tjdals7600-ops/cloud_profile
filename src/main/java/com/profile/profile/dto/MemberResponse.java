package com.profile.profile.dto;

import lombok.Getter;

@Getter
public class MemberResponse {

    private Long id;
    private String membername;
    private int age;
    private String mbti;


    public MemberResponse(Long id, String membername, int age, String mbti) {
        this.id = id;
        this.membername = membername;
        this.age = age;
        this.mbti = mbti;
    }
}
