package com.dao.popup.config;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.dao.popup.dto.response.CommonResponseDto;
import com.dao.popup.enums.BasicResponseData;

@RestControllerAdvice
public class GlobalExceptionHandler {

     // 업로드 최대 용량을 초과했을 경우 에러 처리
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    protected CommonResponseDto handleMultipartException(MaxUploadSizeExceededException ex) {
        // final ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getDetailMessageArguments());
        return CommonResponseDto.builder()
                        .code(BasicResponseData.MAX_SIZE.getCode())
                        .message(BasicResponseData.MAX_SIZE.getMessage())
                        .build();
    }

    // request 값이 다른 형으로 들어왔을 때 에러 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResponseDto handleTypeMismatchException(HttpMessageNotReadableException ex) {
        return CommonResponseDto.builder()
                        .code(BasicResponseData.BAD_REQUEST.getCode())
                        .message(BasicResponseData.BAD_REQUEST.getMessage())
                        .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResponseDto handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return CommonResponseDto.builder()
                        .code(BasicResponseData.METHOD_NOT_ALLOW.getCode())
                        .message(BasicResponseData.METHOD_NOT_ALLOW.getMessage())
                        .build();
    }
}
