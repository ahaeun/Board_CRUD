package com.dao.popup.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ValidResponseDto {
    private String key;
    private String message;
}
