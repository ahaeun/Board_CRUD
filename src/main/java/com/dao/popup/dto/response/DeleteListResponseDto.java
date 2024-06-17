package com.dao.popup.dto.response;

import java.util.List;

import com.dao.popup.enums.BasicResponseData;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DeleteListResponseDto extends CommonResponseDto {
    private List<Integer> data;

    public static DeleteListResponseDto createErrorResponse(List<Integer> paramList, String code, String message) {
        return DeleteListResponseDto.builder()
                                            .data(paramList)
                                            .code(code)
                                            .message(message)
                                            .build();
    }

    public static DeleteListResponseDto createSuccessResponse(List<Integer> paramList, String message) {
        return DeleteListResponseDto.builder()
                                            .data(paramList)
                                            .code(BasicResponseData.SUCCESS.getCode())
                                            .message(message)
                                            .build();
    }
}
