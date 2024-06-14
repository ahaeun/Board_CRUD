package com.dao.popup.dto.response;

import com.dao.popup.dto.PopupDto;
import com.dao.popup.enums.BasicResponseData;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PopupResponseDto extends CommonResponseDto{
    private PopupDto data;

    public static PopupResponseDto createErrorResponse(PopupDto popupDto, String code, String message) {
        return PopupResponseDto.builder()
                                    .data(popupDto)
                                    .code(code)
                                    .message(message)
                                    .build();
    }

    public static PopupResponseDto createSuccessResponse(PopupDto popupDto, String message) {
        return PopupResponseDto.builder()
                                    .data(popupDto)
                                    .code(BasicResponseData.SUCCESS.getCode())
                                    .message(message)
                                    .build();
    }   
}
