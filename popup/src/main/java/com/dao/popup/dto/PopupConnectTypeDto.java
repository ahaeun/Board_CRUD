package com.dao.popup.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopupConnectTypeDto {
    private int popupConnectTypeID;

    @NotBlank(message = "팝업 표시 기기를 입력해주세요.")
    private String popupConnectType;
}
