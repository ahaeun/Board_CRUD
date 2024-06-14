package com.dao.popup.dto.response;

import java.util.List;

import com.dao.popup.enums.BasicResponseData;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CommonErrorListResponseDto extends CommonResponseDto {
    List<CommonResponseDto> error;

    public static CommonErrorListResponseDto createErrorResponse(List<CommonResponseDto> resultDto) {
        return CommonErrorListResponseDto.builder()
                            .code(BasicResponseData.FAIL.getCode())
                            .message(BasicResponseData.FAIL.getMessage())
                            .error(resultDto)
                            .build();
    }
}
