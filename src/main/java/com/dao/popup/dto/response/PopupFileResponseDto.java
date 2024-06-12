package com.dao.popup.dto.response;

import com.dao.popup.dto.PopupDto;
import com.dao.popup.enums.BasicResponseData;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PopupFileResponseDto extends CommonResponseDto {
    private PopupDto data;
    private FileListResponseDto file;

    public static PopupFileResponseDto createErrorResponse(PopupDto popupDto, FileListResponseDto file, String message) {
        return PopupFileResponseDto.builder()
                                    .data(popupDto)
                                    .file(file)
                                    .code(BasicResponseData.FAIL.getCode())
                                    .message(message)
                                    .build();
    }

    public static PopupFileResponseDto createSuccessResponse(PopupDto popupDto, FileListResponseDto file, String message) {
        return PopupFileResponseDto.builder()
                                    .data(popupDto)
                                    .file(file)
                                    .code(BasicResponseData.SUCCESS.getCode())
                                    .message(message)
                                    .build();
    }
}
