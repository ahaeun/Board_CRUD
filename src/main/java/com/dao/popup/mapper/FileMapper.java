package com.dao.popup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dao.popup.dto.FileDto;

@Mapper
public interface FileMapper {
    int insertPopupImage(@Param("list") List<FileDto> list, @Param("popupID") int popupID);
}
