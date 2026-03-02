package com.study.profile_stack_api.global.exception;

import com.study.profile_stack_api.auth.exception.InvalidTokenException;
import com.study.profile_stack_api.global.common.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 사용자 정의 비즈니스 예외
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {

        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.error(errorCode.getCode(), e.getCustomMessage()));
    }

    /**
     * Handle validation errors from @Valid in @RequestBody
     *
     * @RequestBody 검증 실패 시 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        String message = errors.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ErrorCode.INVALID_INPUT.getCode(), message));
    }

    /**
     * Handle validation errors from @PathVariable and @RequestParam
     * @PathVariable, @RequestParam 검증 실패 시 발생
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation (ConstraintViolationException e) {

        String message = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ErrorCode.INVALID_INPUT.getCode(), message));
    }

    /**
     * Handle IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(
            IllegalArgumentException e) {

        ErrorCode errorCode = ErrorCode.INVALID_INPUT;

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }

    /**
     * Handle general exceptions (unexpected errors)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception e) {

        // Log the error for debugging
        e.printStackTrace();

        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException e) {

        ErrorCode errorCode = ErrorCode.BAD_CREDENTIALS;

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidToken(InvalidTokenException e) {

        ErrorCode errorCode = ErrorCode.INVALID_TOKEN;

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }

}
