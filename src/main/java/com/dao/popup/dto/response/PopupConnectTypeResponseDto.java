package com.dao.popup.dto.response;

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

    public static PopupConnectTypeResponseDto createErrorResponse(PopupConnectTypeDto popupConnectTypeDto, String message) {
        return PopupConnectTypeResponseDto.builder()
                                    .data(popupConnectTypeDto)
                                    .code(BasicResponseData.FAIL.getCode())
                                    .message(message)
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
