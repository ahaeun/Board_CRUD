package com.dao.popup.dto.response;

import java.util.List;

import com.dao.popup.enums.BasicResponseData;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PopupConnectTypeListResponseDto extends CommonResponseDto {
    private List<Integer> data;

    public static PopupConnectTypeListResponseDto createErrorResponse(List<Integer> paramList, String message) {
        return PopupConnectTypeListResponseDto.builder()
                                            .data(paramList)
                                            .code(BasicResponseData.FAIL.getCode())
                                            .message(message)
                                            .build();
    }

    public static PopupConnectTypeListResponseDto createSuccessResponse(List<Integer> paramList, String message) {
        return PopupConnectTypeListResponseDto.builder()
                                            .data(paramList)
                                            .code(BasicResponseData.SUCCESS.getCode())
                                            .message(message)
                                            .build();
    }
}
