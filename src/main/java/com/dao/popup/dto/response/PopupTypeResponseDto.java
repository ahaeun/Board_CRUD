package com.dao.popup.dto.response;

import java.util.List;

import com.dao.popup.dto.PopupTypeDto;
import com.dao.popup.enums.BasicResponseData;

import lombok.Getter;
import lombok.Setter;

import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PopupTypeResponseDto extends CommonResponseDto {
    private PopupTypeDto data;
    private List<ValidResponseDto> failMessage;

    public static PopupTypeResponseDto createErrorResponse(PopupTypeDto popupTypeDto, String code, String message, List<ValidResponseDto> failMessage) {
        return PopupTypeResponseDto.builder()
                                    .data(popupTypeDto)
                                    .code(code)
                                    .message(message)
                                    .failMessage(failMessage)
                                    .build();
    }

    public static PopupTypeResponseDto createSuccessResponse(PopupTypeDto popupTypeDto, String message) {
        return PopupTypeResponseDto.builder()
                                    .data(popupTypeDto)
                                    .code(BasicResponseData.FAIL.getCode())
                                    .message(message)
                                    .build();
    }
}
