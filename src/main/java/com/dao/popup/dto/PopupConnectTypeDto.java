package com.dao.popup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopupConnectTypeDto {
    @PositiveOrZero(message = "팝업 기기 타입은 0또는 양수만 가능합니다.")
    private int popupConnectTypeID;

    @NotBlank(message = "팝업 표시 기기를 입력해주세요.")
    private String popupConnectType;
}
