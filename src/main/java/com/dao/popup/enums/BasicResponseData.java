package com.dao.popup.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor //생성자 자동 주입 어노테이션
public enum BasicResponseData {
    
    SUCCESS("SUCCESS", "200"),
    FAIL("FAIL", "500"),
    BAD_REQUEST("BAD_REQUEST", "400"),
    ACCESS_DENEIED("FORBIDDEN", "403"),
    METHOD_NOT_ALLOW("METHOD_NOT_ALLOW", "405"),
    DUPLICATE_RESOURCE("DUPLICATE_RESOURCE", "409"),
    MAX_SIZE("PAYLOAD TOO LARGE", "413");

    private final String message;
    private final String code;
    
}
