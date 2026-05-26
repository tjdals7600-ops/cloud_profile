package com.profile.profile.controller;

import com.profile.profile.dto.MemberCreateRequest;
import com.profile.profile.dto.MemberDetailResponse;
import com.profile.profile.dto.MemberResponse;
import com.profile.profile.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/members")
public class MemberController {

    // 속성
    private final MemberService memberService;

   // 생성자
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    // 팀원 등록
    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@RequestBody MemberCreateRequest request) {
        log.info("[API - LOG] 팀원 등록 요청 - membername: {}, age: {}, mbti: {}",
                request.getMembername(),
                request.getAge(),
                request.getMbti());
        MemberResponse memberResponseDto = memberService.createMember(request);
        ResponseEntity<MemberResponse> response = new ResponseEntity<>(memberResponseDto, HttpStatus.CREATED);
        return response;
    }


    // 팀원 상세 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDetailResponse> getDetailMember(@PathVariable Long memberId) {
        log.info("[API - LOG] 팀원 상세 조회 요청 - memberId: {}", memberId);
        MemberDetailResponse memberDetailResponseDto = memberService.getDetailMember(memberId);
        ResponseEntity<MemberDetailResponse> detailresponse = new ResponseEntity<>(memberDetailResponseDto, HttpStatus.OK);
        return detailresponse;

    }

    // 프로필 이미지 업로드
    @PostMapping("/{memberId}/profile-image")
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable Long memberId,
            @RequestParam("file") MultipartFile file
    ) {
        memberService.uploadProfileImage(memberId, file);
        return ResponseEntity.ok("프로필 이미지 업로드 완료!.");
    }

    @GetMapping("/{memberId}/profile-image")
    public ResponseEntity<String> getProfileImage(
            @PathVariable Long memberId
    ) {
        String imageUrl = memberService.getProfileImageUrl(memberId);

        return ResponseEntity.ok(imageUrl);
    }



}
