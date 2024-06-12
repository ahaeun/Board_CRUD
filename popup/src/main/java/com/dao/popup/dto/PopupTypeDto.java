package com.dao.popup.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  PopupTypeDto {
    private int popupTypeID;

    @NotBlank(message = "팝업 표시 타입을 입력해주세요.")
    private String popupType;
}
