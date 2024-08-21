package com.dao.popup.dto.response;

import org.springframework.validation.ObjectError;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CommonListResponseDto {
    private String code;
    private List<ObjectError> message;
}
