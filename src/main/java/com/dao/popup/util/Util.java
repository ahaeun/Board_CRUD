package com.dao.popup.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

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
}
