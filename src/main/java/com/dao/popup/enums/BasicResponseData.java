package com.dao.popup.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor //생성자 자동 주입 어노테이션
public enum BasicResponseData {
    
    SUCCESS("SUCCESS", "200"),
    FAIL("FAIL", "500");

    private final String message;
    private final String code;
    
}
