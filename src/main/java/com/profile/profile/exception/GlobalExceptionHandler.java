package com.profile.profile.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {

        // error 로그 - 스택트레이스 출력
        log.error("[ERROR] 서버 오류 발생", e);

        return new ResponseEntity<>("서버에 오류가 발생했습니다.",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
