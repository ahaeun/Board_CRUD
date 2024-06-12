package com.dao.popup.dto;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestListDto<T> {
    
    private T data;
    private Pageable pageable;

}
