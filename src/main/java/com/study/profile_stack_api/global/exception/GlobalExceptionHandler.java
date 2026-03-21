package com.study.profile_stack_api.global.exception;

import com.study.profile_stack_api.domain.profile.exception.ResourceNotFoundException;
import com.study.profile_stack_api.global.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.h2.api.ErrorCode;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException e) {
        log.warn("리소스 없음 : {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("RESOURCE_NOT_FOUND", e.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        log.warn("유효성 검증 실패: {}", errors);

        String message = errors.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest()
                .body(ApiResponse.error("VALIDATION_ERROR", message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(
            IllegalArgumentException e) {
        log.warn("잘못된 인자 : {}", e.getMessage());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error("INVALID_ARGUMENT", e.getMessage()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateKey(DuplicateKeyException e) {
        log.warn("중복 키 발생 : {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error("DUPLICATE_RESOURCE", e.getMessage()));
    }

    // !! 전역 에러 처리. 상단에서 Handler 작성할 것 !!
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handlerGeneral(Exception e) {
        log.error("예상치 못한 예외 발생", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("INTERNAL_SERVER_ERROR", "SERVER_ERROR"));
    }
}
