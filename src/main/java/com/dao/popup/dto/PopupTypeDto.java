package com.dao.popup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  PopupTypeDto {
    @PositiveOrZero(message = "팝업 타입 아이디는 0또는 양수만 가능합니다.")
    private int popupTypeID;

    @NotBlank(message = "팝업 표시 타입을 입력해주세요.")
    private String popupType;
}
