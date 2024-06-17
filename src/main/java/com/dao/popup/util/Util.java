package com.dao.popup.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.dao.popup.dto.response.ValidResponseDto;

@Component
public class Util {
    public static final String SEEDKEY = "password12345678";

    // 확장자 분리
    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
          .filter(f -> f.contains("."))
          .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    // 파일 이미지 분리
    public Optional<String> getImageNameByStringHandling(String filename) {
        return Optional.ofNullable(filename)
          .filter(f -> f.contains("."))
          .map(f -> f.substring(0, filename.lastIndexOf(".")));
    }

    // 이미지 파일 확장자 목록
    public static final List<String> IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");

    // 유효성 체크에서 걸린 실패 필드 리스트에 담기
    public static List<ValidResponseDto> validFailList(Errors errors) {
      if(errors.hasErrors()){ // NotBlank 예외 처리
            List<ValidResponseDto> failList = new ArrayList<>();
            for(FieldError e : errors.getFieldErrors()) {
                ValidResponseDto vdto = ValidResponseDto.builder()
                                            .key(e.getField())
                                            .message(e.getDefaultMessage())
                                            .build();
                failList.add(vdto);
            }
            return failList;
      }else {
        return null;
      }
    }
}
