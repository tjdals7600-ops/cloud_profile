package com.profile.profile.service;

import com.profile.profile.dto.MemberCreateRequest;
import com.profile.profile.dto.MemberDetailResponse;
import com.profile.profile.dto.MemberResponse;
import com.profile.profile.entity.Member;
import com.profile.profile.repository.MemberRepository;
import com.profile.profile.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    // 속성
    private final MemberRepository memberRepository;
    private final S3Service s3Service;


    // 팀원 등록
    @Transactional

    // 0. 컨트롤러에서 데이터 받아오기
    public MemberResponse createMember(MemberCreateRequest request) {

        // 1. 받아온 데이터 준비
        String newMemberName = request.getMembername();
        int newAge = request.getAge();
        String newMbti = request.getMbti();

        // 2.저장할 팀원 만들기
        Member newMember = new Member(newMemberName, newAge, newMbti);

        // 3. 저장하기 - 저장된 팀원 정보 받기
        Member createMember = memberRepository.save(newMember);

        // 4. 저장된 데이터 준비
        Long createdMemberId = createMember.getId();
        String createdMemberName = createMember.getMembername();
        int createdAge = createMember.getAge();
        String createdMbti = createMember.getMbti();

        // 5. 응답 dto 만들기
        MemberResponse memberResponse = new MemberResponse(
                createdMemberId,
                createdMemberName,
                createdAge,
                createdMbti
        );

        // 6. 반환하기
        return memberResponse;
    }

    // 팀원 상세 조회
    @Transactional(readOnly = true)

    // 0. 컨트롤러에서 데이터 받아오기
    public MemberDetailResponse getDetailMember(Long id) {
        // 1. db에서 id로 팀원 조회
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("팀원을 찾을 수 없습니다."));

        // 2. 데이터 준비
        Long responseId = member.getId();
        String responseMemberName = member.getMembername();
        int responseAge = member.getAge();
        String responseMbti = member.getMbti();

        // 3. 응답 dto 만들기
        MemberDetailResponse detailResponse = new MemberDetailResponse(
                responseId,
                responseMemberName,
                responseAge,
                responseMbti
        );
        // 4. 반환하기
        return detailResponse;
    }

    // 이미지 업로드
    @Transactional
    public void uploadProfileImage(Long memberId, MultipartFile file) {

        // 회원 조회하기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        // s3에 업로드 하기
        String key = s3Service.upload(file);

        // db에 업로드한 파일 저장
        member.updateProfileImage(key);

        memberRepository.save(member
        );
    }

    // 프로필 이미지 조회
    @Transactional(readOnly = true)
    public String getProfileImageUrl(Long memberId) {

        // 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        // 저장된 s3 key 가져오기
        String key = member.getProfileImage();

        // presigned URL 생성하기 후 반환

        return s3Service.getDownloadUrl(key);

    }
}
