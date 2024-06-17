package com.dao.popup.dto.response;

import java.util.List;

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
    private List<ValidResponseDto> failMessage;

    public static PopupResponseDto createErrorResponse(PopupDto popupDto, String code, String message, List<ValidResponseDto> failMessage) {
        return PopupResponseDto.builder()
                                    .data(popupDto)
                                    .code(code)
                                    .failMessage(failMessage)
                                    .message(BasicResponseData.FAIL.getMessage())
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
