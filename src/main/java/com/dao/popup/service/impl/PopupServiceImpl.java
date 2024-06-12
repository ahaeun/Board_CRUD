package com.dao.popup.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.dao.popup.dto.PageDto;
import com.dao.popup.dto.PopupConnectTypeDto;
import com.dao.popup.dto.PopupTypeDto;
import com.dao.popup.dto.PopupDto;
import com.dao.popup.dto.RequestListDto;
import com.dao.popup.dto.response.FileListResponseDto;
import com.dao.popup.dto.response.PopupConnectTypeListResponseDto;
import com.dao.popup.dto.response.PopupConnectTypeResponseDto;
import com.dao.popup.dto.response.PopupListResponseDto;
import com.dao.popup.dto.response.PopupResponseDto;
import com.dao.popup.dto.response.PopupTypeListResponseDto;
import com.dao.popup.dto.response.PopupTypeResponseDto;

import com.dao.popup.enums.BasicResponseData;

import com.dao.popup.mapper.FileMapper;
import com.dao.popup.mapper.PopupMapper;

import com.dao.popup.service.FileService;
import com.dao.popup.service.PopupService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Service
public class PopupServiceImpl implements PopupService{

    @Autowired
    FileService fileService;

    @Autowired
    PopupMapper popupMapper;

    @Autowired
    FileMapper fileMapper;

	@Override
	public Map<String, Object> selectPopupList(String page, PopupDto popupDto) {

        // page 예외 처리(음수, 문자열)
        int pageNumber;
        try{
            pageNumber = Integer.parseInt(page);
            pageNumber = Math.max(pageNumber - 1, 0); // 0 이하면 0으로 초기화
        }catch(NumberFormatException e){ // 페이지가 문자열로 넘어왔을 경우
            pageNumber = 0;
        }
        Pageable pageable =PageRequest.of(pageNumber, 5);
		
        int total = popupMapper.popupListCount(popupDto);
        Map<String, Object> popupMap = new HashMap<>();
        RequestListDto<?> requestList = RequestListDto.builder()
            .data(popupDto)
            .pageable(pageable)
            .build();

        List<PopupDto> content = popupMapper.selectPopupList(requestList);
        Page<PopupDto> popupPageList = new PageImpl<>(content, pageable, total);
        PageDto pageDto = PageDto.builder()
                            .blockSize(popupPageList.getSize())
                            .listSize(popupPageList.getTotalPages())
                            .page(popupPageList.getNumber())
                            .listCount(total)
                            .build();
        popupMap.put("pageDto", pageDto); // 페이지 관련 데이터
        popupMap.put("popupPageList", popupPageList); // 페이징한 데이터 리스트
  
        return popupMap;

	}

    @Override
    public PopupResponseDto selectPopup(String popupStringID) {
        try{
            int popupID = Integer.parseInt(popupStringID);

            PopupDto selectPopup = popupMapper.selectPopup(popupID);
            if(selectPopup == null || selectPopup.getPopupID() == -1){ // 존재하지 않는 팝업일 경우
                PopupResponseDto resultDto = PopupResponseDto.createErrorResponse(selectPopup, BasicResponseData.FAIL.getMessage());
                return resultDto;
            }else {
                PopupResponseDto resultDto = PopupResponseDto.createSuccessResponse(selectPopup, BasicResponseData.SUCCESS.getMessage());
                return resultDto;
            }
        }catch(Exception e) {
            PopupResponseDto resultDto = PopupResponseDto.createErrorResponse(null, "잘못된 작업이 요청되었습니다.");
            return resultDto;
        }
    }

    @Override
    public PopupResponseDto insertPopup(PopupDto popupDto, PopupResponseDto resultDto, FileListResponseDto fileListResponseDto) throws Exception {

        popupMapper.insertPopup(popupDto);

        if (popupDto.getPopupID() > 0) { // 팝업 등록 성공
            if(fileListResponseDto.getSuccessData().size() > 0){ // 첨부파일이 있을 경우
                fileMapper.insertPopupImage(fileListResponseDto.getSuccessData(), popupDto.getPopupID());
                resultDto = PopupResponseDto.createSuccessResponse(popupDto, BasicResponseData.SUCCESS.getMessage());
            }else { // 첨부파일이 없을 경우
                resultDto = PopupResponseDto.createSuccessResponse(popupDto, BasicResponseData.SUCCESS.getMessage());
            }
        }else {
            resultDto = PopupResponseDto.createErrorResponse(popupDto, BasicResponseData.FAIL.getMessage());
        }

        return resultDto;
    }

    @Override
    public PopupResponseDto updatePopup(PopupDto popupDto, PopupResponseDto resultDto, FileListResponseDto fileListResponseDto) {
        int result = popupMapper.updatePopup(popupDto);

        if(result == 1){
            if(fileListResponseDto.getSuccessData() != null){ // 첨부파일이 있을 경우
                popupMapper.insertPopupImage(fileListResponseDto.getSuccessData(), popupDto.getPopupID());
                resultDto = PopupResponseDto.createSuccessResponse(popupDto, BasicResponseData.SUCCESS.getMessage());
            }else { // 첨부파일이 없을 경우
                resultDto = PopupResponseDto.createSuccessResponse(popupDto, BasicResponseData.SUCCESS.getMessage());
            }
        }else {
            resultDto = PopupResponseDto.createErrorResponse(popupDto, BasicResponseData.FAIL.getMessage());
        }
        return resultDto;
    }

    @Override
    public PopupListResponseDto deletePopupList(HttpServletRequest request) {
        PopupListResponseDto resultDto = PopupListResponseDto.builder().build();
        String[] paramArr = request.getParameterValues("popupID");

        if(!paramArr[0].isEmpty()){
            List<String> paramList = Arrays.asList(paramArr);
            List<Integer> intParamList = new ArrayList<>();

            //requestID를 int형으로 변환
            try {
                intParamList = paramList.stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
                int result = popupMapper.deletePopup(intParamList);

                if(result > 0) { //성공
                    resultDto = PopupListResponseDto.createSuccessResponse(intParamList, BasicResponseData.SUCCESS.getMessage());
                }else {
                    resultDto = PopupListResponseDto.createErrorResponse(intParamList, BasicResponseData.FAIL.getMessage());
                }
            }catch(Exception e) { // 문자열로 들어왔을 경우
                resultDto = PopupListResponseDto.createErrorResponse(intParamList, "잘못된 작업이 요청되었습니다.");
            }
            
        }else{
            resultDto = PopupListResponseDto.createErrorResponse(null, "삭제할 팝업을 선택해주세요.");
        }
        return resultDto;
    }

    @Override
    public List<PopupTypeDto> selectPopupType() {
        return popupMapper.selectPopupType();
    }

    @Override
    public List<PopupConnectTypeDto> selectPopupCoonectType() {
        return popupMapper.selectPopupConnectType();
    }

    @Override
    public void selectPopupForm(PopupDto popupDto, Model model) {

        PopupResponseDto resultDto = PopupResponseDto.builder().build();

        if (popupDto != null && popupDto.getPopupID() != 0) { // 성공
            model.addAttribute("popupType", selectPopupType());
            model.addAttribute("popupConnectType", selectPopupCoonectType());
            resultDto = PopupResponseDto.createSuccessResponse(popupDto, BasicResponseData.SUCCESS.getMessage());
            model.addAttribute("result", resultDto);
        }else if(popupDto == null) { // 존재하지 않는 게시판 일 경우 예외 처리
            resultDto = PopupResponseDto.createErrorResponse(null, "존재하지 않는 게시판입니다.");
            model.addAttribute("result", resultDto);
        }else { // 폼 불러오기 실패 예외 처리
            resultDto = PopupResponseDto.createErrorResponse(null, "잘못된 작업이 요청되었습니다.");
            model.addAttribute("result", resultDto);
        }
    }

    @Override
    public List<PopupTypeDto> selectPopupTypeList() {
        return popupMapper.selectPopupTypeList();
    }

    @Override
    public PopupTypeResponseDto insertPopupType(@Valid PopupTypeDto popupTypeDto, PopupTypeResponseDto resultDto) {
        popupMapper.insertPopupType(popupTypeDto);

        if (popupTypeDto.getPopupTypeID() != 0) { // 팝업 등록 성공
            resultDto = PopupTypeResponseDto.createSuccessResponse(popupTypeDto, BasicResponseData.SUCCESS.getMessage());
        }else {
            resultDto = PopupTypeResponseDto.createErrorResponse(popupTypeDto, BasicResponseData.FAIL.getMessage());
        }

        return resultDto;
    }

    @Override
    public PopupTypeResponseDto updatePopupType(@Valid PopupTypeDto popupTypeDto, PopupTypeResponseDto resultDto) {
        int result = popupMapper.updatePopupType(popupTypeDto);

        if(result == 1){
            resultDto = PopupTypeResponseDto.createSuccessResponse(popupTypeDto, BasicResponseData.SUCCESS.getMessage());
        }else {
            resultDto = PopupTypeResponseDto.createErrorResponse(popupTypeDto, BasicResponseData.FAIL.getMessage());
        }
        return resultDto;
    }

    @Override
    public PopupTypeListResponseDto deletePopupTypeList(HttpServletRequest request) {
        PopupTypeListResponseDto resultDto = PopupTypeListResponseDto.builder().build();
        String[] paramArr = request.getParameterValues("popupTypeID");

        if(!paramArr[0].isEmpty()){
            List<String> paramList = Arrays.asList(paramArr);
            List<Integer> intParamList = new ArrayList<>();

            //requestID를 int형으로 변환
            try {
                intParamList = paramList.stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
                int result = popupMapper.deletePopupType(intParamList);

                if(result > 0) { //성공
                    resultDto = PopupTypeListResponseDto.createSuccessResponse(intParamList, BasicResponseData.SUCCESS.getMessage());
                }else {
                    resultDto = PopupTypeListResponseDto.createErrorResponse(intParamList, BasicResponseData.FAIL.getMessage());
                }
            }catch(Exception e) { // 문자열로 들어왔을 경우
                resultDto = PopupTypeListResponseDto.createErrorResponse(intParamList, "잘못된 작업이 요청되었습니다.");
            }
            
        }else{
            resultDto = PopupTypeListResponseDto.createErrorResponse(null, "삭제할 팝업을 선택해주세요.");
        }
        return resultDto;
    }

    @Override
    public List<PopupConnectTypeDto> selectPopupConnectTypeList() {
        return popupMapper.selectPopupConnectTypeList();
    }

    @Override
    public PopupConnectTypeResponseDto insertPopupConnectType(@Valid PopupConnectTypeDto popupConnectTypeDto,
            PopupConnectTypeResponseDto resultDto) {
                popupMapper.insertPopupConnectType(popupConnectTypeDto);

                if (popupConnectTypeDto.getPopupConnectTypeID() != 0) { // 팝업 등록 성공
                    resultDto = PopupConnectTypeResponseDto.createSuccessResponse(popupConnectTypeDto, BasicResponseData.SUCCESS.getMessage());
                }else {
                    resultDto = PopupConnectTypeResponseDto.createErrorResponse(popupConnectTypeDto, BasicResponseData.FAIL.getMessage());
                }
        
                return resultDto;
    }

    @Override
    public PopupConnectTypeResponseDto updatePopupConnectType(@Valid PopupConnectTypeDto popupConnectTypeDto, PopupConnectTypeResponseDto resultDto) {
                int result = popupMapper.updatePopupConnectType(popupConnectTypeDto);

                if(result == 1){
                    resultDto = PopupConnectTypeResponseDto.createSuccessResponse(popupConnectTypeDto, BasicResponseData.SUCCESS.getMessage());
                }else {
                    resultDto = PopupConnectTypeResponseDto.createErrorResponse(popupConnectTypeDto, BasicResponseData.FAIL.getMessage());
                }
                return resultDto;
    }

    @Override
    public PopupConnectTypeListResponseDto deletePopupConnectTypeList(HttpServletRequest request) {
        PopupConnectTypeListResponseDto resultDto = PopupConnectTypeListResponseDto.builder().build();
        String[] paramArr = request.getParameterValues("popupConnectTypeID");

        if(!paramArr[0].isEmpty()){
            List<String> paramList = Arrays.asList(paramArr);
            List<Integer> intParamList = new ArrayList<>();

            //requestID를 int형으로 변환
            try {
                intParamList = paramList.stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
                int result = popupMapper.deletePopupConnectType(intParamList);

                if(result > 0) { //성공
                    resultDto = PopupConnectTypeListResponseDto.createSuccessResponse(intParamList, BasicResponseData.SUCCESS.getMessage());
                }else {
                    resultDto = PopupConnectTypeListResponseDto.createErrorResponse(intParamList, BasicResponseData.FAIL.getMessage());
                }
            }catch(Exception e) { // 문자열로 들어왔을 경우
                resultDto = PopupConnectTypeListResponseDto.createErrorResponse(intParamList, "잘못된 작업이 요청되었습니다.");
            }
            
        }else{
            resultDto = PopupConnectTypeListResponseDto.createErrorResponse(null, "삭제할 항목을 선택해주세요.");
        }
        return resultDto;
    }

    @Override
    public FileListResponseDto createFile(List<MultipartFile> multipartList, int popupID, String path) throws Exception {
        FileListResponseDto resultDto = FileListResponseDto.builder().build();
        if(path == null || path.equals("")) {
            path = "popup";
        }

        // 최대 10개까지 이미지 업로드 가능
        if(multipartList.size() > 0 && multipartList.size() < 11){
            resultDto = fileService.createFile(multipartList, popupID, path);
        }else if(multipartList.size() > 10) {
            resultDto = FileListResponseDto.createErrorResponse(null, null, "파일은 10개까지 업로드 가능합니다.");
        }else {
            resultDto = FileListResponseDto.createErrorResponse(null, null, "잘못된 요청입니다.");
        }
        return resultDto;
    }
}
