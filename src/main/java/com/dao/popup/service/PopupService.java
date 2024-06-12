package com.dao.popup.service;


import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.dao.popup.dto.PopupConnectTypeDto;
import com.dao.popup.dto.PopupDto;
import com.dao.popup.dto.PopupTypeDto;
import com.dao.popup.dto.response.FileListResponseDto;
import com.dao.popup.dto.response.PopupConnectTypeListResponseDto;
import com.dao.popup.dto.response.PopupConnectTypeResponseDto;
import com.dao.popup.dto.response.PopupListResponseDto;
import com.dao.popup.dto.response.PopupResponseDto;
import com.dao.popup.dto.response.PopupTypeListResponseDto;
import com.dao.popup.dto.response.PopupTypeResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface PopupService {

    //팝업 게시판 CRUD
    Map<String, Object> selectPopupList(String page, PopupDto popupDto);

    PopupResponseDto insertPopup(PopupDto popupDto, PopupResponseDto resultDto, FileListResponseDto fileListResponseDto) throws Exception;

    PopupResponseDto updatePopup(PopupDto popupDto, PopupResponseDto resultDto, FileListResponseDto fileListResponseDto);

    PopupListResponseDto deletePopupList(HttpServletRequest request);

    PopupResponseDto selectPopup(String popupStringID);

    List<PopupTypeDto> selectPopupType();

    List<PopupConnectTypeDto> selectPopupCoonectType();

    void selectPopupForm(PopupDto popupDto, Model model);

    List<PopupTypeDto> selectPopupTypeList();


    // 팝업 표시 사이트 타입 CRUD
    PopupTypeResponseDto insertPopupType(@Valid PopupTypeDto popupTypeDto, PopupTypeResponseDto resultDto);

    PopupTypeResponseDto updatePopupType(@Valid PopupTypeDto popupTypeDto, PopupTypeResponseDto resultDto);

    PopupTypeListResponseDto deletePopupTypeList(HttpServletRequest request);

    // 팝업 표시 기기 타입 CRUD
    List<PopupConnectTypeDto> selectPopupConnectTypeList();

    PopupConnectTypeResponseDto insertPopupConnectType(@Valid PopupConnectTypeDto popupConnectTypeDto, PopupConnectTypeResponseDto resultDto);

    PopupConnectTypeResponseDto updatePopupConnectType(@Valid PopupConnectTypeDto popupConnectTypeDto, PopupConnectTypeResponseDto resultDto);

    PopupConnectTypeListResponseDto deletePopupConnectTypeList(HttpServletRequest request);

    FileListResponseDto createFile(List<MultipartFile> multipartList, int popupID, String path) throws Exception;
}
