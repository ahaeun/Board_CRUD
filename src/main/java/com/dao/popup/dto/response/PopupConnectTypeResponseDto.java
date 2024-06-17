package com.dao.popup.dto.response;

import java.util.List;

import com.dao.popup.dto.PopupConnectTypeDto;
import com.dao.popup.enums.BasicResponseData;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PopupConnectTypeResponseDto extends CommonResponseDto {
    private PopupConnectTypeDto data;
    private List<ValidResponseDto> failMessage;

    public static PopupConnectTypeResponseDto createErrorResponse(PopupConnectTypeDto popupConnectTypeDto, String code, String message, List<ValidResponseDto> failMessage) {
        return PopupConnectTypeResponseDto.builder()
                                    .data(popupConnectTypeDto)
                                    .code(code)
                                    .message(message)
                                    .failMessage(failMessage)
                                    .build();
    }

    public static PopupConnectTypeResponseDto createSuccessResponse(PopupConnectTypeDto popupConnectTypeDto, String message) {
                return PopupConnectTypeResponseDto.builder()
                                    .data(popupConnectTypeDto)
                                    .code(BasicResponseData.SUCCESS.getCode())
                                    .message(message)
                                    .build();
    }
}
