package com.dao.popup.dto;

import org.springframework.format.annotation.DateTimeFormat;

import com.dao.popup.dto.response.FileListResponseDto;
import com.dao.popup.enums.BooleanFlag;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopupDto {
    
    @PositiveOrZero(message = "팝업 타입은 0또는 양수만 가능합니다.")
    private Integer popupID;

    @NotNull(message = "팝업이 표시될 페이지 유형을 선택해주세요.")
    @Max(value = 255, message = "초과되었습니다.")
    @PositiveOrZero(message = "팝업 타입은 0또는 양수만 가능합니다.")
    private Integer popupTypeID;

    @NotNull(message = "팝업이 표시될 기기를 선택해주세요.")
    @Max(value = 255, message = "초과되었습니다.")
    @PositiveOrZero(message = "팝업 타입은 0또는 양수만 가능합니다.")
    private Integer connectTypeID;

    @NotNull(message = "작성자를 입력해주세요.")
    private String adminID;

    @NotBlank(message = "제목이 비어있습니다.")
    @Size(max = 100, message = "제목 글자수가 초과했습니다.")
    private String popupTitle;

    @NotBlank(message = "내용이 비어있습니다.")
    @Size(max = 3000, message = "내용 글자수가 초과했습니다.")
    private String popupContent;

    @NotNull(message = "다시보지않음 체크 시 시간을 설정해주세요.")
    private String noWatchTime;

    private String createAt;
    
    @NotBlank(message = "시작일시를 적어주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startAt;

    @NotBlank(message = "종료일시를 적어주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endAt;

    @PositiveOrZero(message = "0또는 양수만 가능합니다.")
    private String leftPosition;

    @PositiveOrZero(message = "0또는 양수만 가능합니다.")
    private String topPosition;

    @PositiveOrZero(message = "0또는 양수만 가능합니다.")
    private String width;

    @PositiveOrZero(message = "0또는 양수만 가능합니다.")
    private String height;

    private String updateAt;

    private BooleanFlag isDelete;

    private FileListResponseDto imageList;

}
