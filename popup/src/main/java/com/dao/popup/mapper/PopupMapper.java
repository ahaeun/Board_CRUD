package com.dao.popup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dao.popup.dto.FileDto;
import com.dao.popup.dto.PopupConnectTypeDto;
import com.dao.popup.dto.PopupTypeDto;
import com.dao.popup.dto.PopupDto;
import com.dao.popup.dto.RequestListDto;

import jakarta.validation.Valid;

@Mapper
public interface PopupMapper {

    //팝업 게시판 CRUD
    int popupListCount(PopupDto popupDto);

    List<PopupDto> selectPopupList(RequestListDto<?> requestList);

    int insertPopup(PopupDto popupDto);

    int updatePopup(PopupDto popupDto);

    int deletePopup(@Param("paramList") List<Integer> paramList);

    PopupDto selectPopup(int popupNo);

    int insertPopupImage(@Param("list") List<FileDto> list, @Param("popupID") int popupID);

    List<PopupTypeDto> selectPopupType();

    List<PopupConnectTypeDto> selectPopupConnectType();

    // 팝업 표시 사이트 타입 CRUD
    List<PopupTypeDto> selectPopupTypeList();
    
    int insertPopupType(PopupTypeDto popupTypeDto);

    int updatePopUpType(PopupTypeDto popupTypeDto);

    int deletePopupType(@Param("paramList") List<Integer> paramList);

    int updatePopupType(@Valid PopupTypeDto popupTypeDto);

    // 팝업 표시 기기 타입 CRUD
    List<PopupConnectTypeDto> selectPopupConnectTypeList();

    void insertPopupConnectType(@Valid PopupConnectTypeDto popupConnectTypeDto);

    int updatePopupConnectType(@Valid PopupConnectTypeDto popupConnectTypeDto);

    int deletePopupConnectType(@Param("paramList") List<Integer> paramList);

    List<PopupDto> injectionTest(String injection);
}
