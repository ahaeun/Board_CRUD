package com.dao.popup.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageDto {

    private int blockSize; // 한 페이지의 데이터 수
    private int listSize; // 총 페이지 수
    private int page; // 현재 페이지
    private int listCount; // 총 데이터 수

}
