package com.goruna.spring.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException e) {
        logger.error("CustomException occurred: {}", e.getErrorCode().getMessage(), e);
        return ResponseUtil.failureResponse(e.getErrorCode().getMessage(), e.getErrorCode().getCode(), e.getErrorCode().getHttpStatus());
    }

    // RuntimeException 처리
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException e) {
        logger.error("RuntimeException occurred: {}", e.getMessage(), e);
        return ResponseUtil.failureResponse("Unexpected runtime error occurred.", "RUNTIME_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 데이터베이스 관련 예외 처리
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<String>> handleDataAccessException(DataAccessException e) {
        logger.error("DataAccessException occurred: {}", e.getMessage(), e);
        return ResponseUtil.failureResponse("Database error occurred", "DB_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 유효성 검증 실패 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        logger.error("Validation error occurred: {}", errorMessage);
        return ResponseUtil.failureResponse("Validation error: " + errorMessage, "VALIDATION_ERROR", HttpStatus.BAD_REQUEST);
    }

    // 권한 관련 예외 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException e) {
        logger.warn("AccessDeniedException occurred: {}", e.getMessage());
        return ResponseUtil.failureResponse("Access denied", "ACCESS_DENIED", HttpStatus.FORBIDDEN);
    }

    // 기타 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception e) {
        logger.error("Unexpected error occurred: {}", e.getMessage(), e);
        return ResponseUtil.failureResponse("An internal error occurred. Please contact support.", "INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
