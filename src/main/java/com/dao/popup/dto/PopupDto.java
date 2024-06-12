package com.dao.popup.dto;

import com.dao.popup.dto.response.FileListResponseDto;
import com.dao.popup.enums.BooleanFlag;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopupDto {
    
    private int popupID;

    private int popupTypeID;

    private int connectTypeID;

    private String adminID;

    @NotBlank(message = "제목이 비어있습니다.")
    private String popupTitle;

    @NotBlank(message = "내용이 비어있습니다.")
    private String popupContent;

    private String noWatchTime;

    private String createAt;
    
    private String startAt;

    private String endAt;

    private String leftPosition;

    private String topPosition;

    private String width;

    private String height;

    private String updateAt;

    private BooleanFlag isDelete;

    private FileListResponseDto imageList;

}
