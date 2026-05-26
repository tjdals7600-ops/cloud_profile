package com.profile.profile.s3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    // prod에 있는 region 가져오기
    @Value("${spring.cloud.aws.region.static}")
    private String region;

    // bean으로 S3Client 객체 등록
    @Bean
    public S3Client s3Client() {

        // s3client 생성
        return S3Client.builder()

                //aws 서울로 리전 설정 사용
                .region(Region.of(region))

                //
                .build();


    }
    @Bean
    public S3Presigner s3Presigner() {

        return S3Presigner.builder()
                .region(Region.of(region))
                .build();
    }
}
