package com.dao.popup.dto.response;

import java.util.List;

import com.dao.popup.dto.FileDto;
import com.dao.popup.enums.BasicResponseData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileListResponseDto extends CommonResponseDto {
    private List<FileDto> successData;
    private List<FileDto> failData;

    public static FileListResponseDto createErrorResponse(List<FileDto> successDtoList, List<FileDto> failDtoList, String code, String message) {
        return FileListResponseDto.builder()
                                    .successData(successDtoList)
                                    .failData(failDtoList)
                                    .code(code)
                                    .message(message)
                                    .build();
    }

    public static FileListResponseDto createSuccessResponse(List<FileDto> successDtoList, String message) {
        return FileListResponseDto.builder()
                                    .successData(successDtoList)
                                    .failData(null)
                                    .code(BasicResponseData.SUCCESS.getCode())
                                    .message(message)
                                    .build();
    }
    
}
