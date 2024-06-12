package com.dao.popup.controller;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.dao.popup.service.FileService;
import com.dao.popup.service.PopupService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/dao_adm/popup")
public class PopUpController {
    @Autowired
    PopupService popupService;

    @Autowired
    FileService fileService;

    /*
     *  @description : 팝업 목록
     *  
     *  page로 현재 페이지의 번호를 가져와 Pageable 인터페이스를 사용하여
     *  삭제 되지 않은 팝업 게시물만 페이징하여 가져오는 기능입니다.
     */
    @GetMapping("/popupList/{page}")
    public String postMethodName(@PathVariable("page") String page, PopupDto PopupDto, Model model) {

        int pageNumber = 0;
        Pageable pageable = null;

        try{
            pageNumber = Integer.parseInt(page);
        }catch(NumberFormatException e){ // 페이지가 문자열로 넘어왔을 경우
            pageNumber = 0;
        }

        // 페이지 0이거나 음수일 경우 예외처리
        if(pageNumber > 0) {
            pageable = PageRequest.of(pageNumber - 1, 5);
        }else {
            pageable = PageRequest.of(0, 5);
        }

        Map<String, Object> popupMap = popupService.selectPopupList(PopupDto, pageable);

        model.addAttribute("popupPageList", popupMap.get("popupPageList"));
        model.addAttribute("pageDto", popupMap.get("pageDto"));
        
        return "popup/popupList.admin";
    }

    /*
     *  @description : 팝업 단건 조회
     *  팝업 ID를 가져와 해당 팝업 게시판을 조회하는 폼으로 이동.
     */
    @GetMapping("/select/{popupID}")
    public String popupSelect(Model model, @PathVariable("popupID") String popupStringID) {

        int popupID = 0;
        PopupResponseDto resultDto = PopupResponseDto.builder().build();

        try{
            popupID = Integer.parseInt(popupStringID);
        }catch(Exception e) {
            resultDto = PopupResponseDto.createErrorResponse(null, "잘못된 작업이 요청되었습니다.");
        }

        resultDto = popupService.selectPopup(popupID, resultDto);
        model.addAttribute("result", resultDto);

        return "popup/popupSelect.admin";
    }

    /*
     *  @description : 팝업 등록 폼
     *  팝업 등록 폼(popupWrite.admin.html)으로 이동하는 컨트롤러입니다.
     */
    @GetMapping(value = "/write")
    public String writeform(Model model) {
        model.addAttribute("popupType", popupService.selectPopupType());
        model.addAttribute("popupConnectType", popupService.selectPopupCoonectType());
        return "popup/popupWrite.admin";
    }

    /*
     *  @description : 팝업 저장
     *  팝업을 저장하는 기능입니다.
     *  첨부 파일을 저장, 이동(utile.Path.java)과 파일 이름을 Seed128로 암호화하여 저장하는 기능입니다.
     *  팝업 ID를 가져와 0이면 추가, 0이 아니면 수정으로 나누어 저장, 수정합니다.
     *  [이미지 저장 로직]
     *  1. 이미지 저장 시 이미지가 해당 폴더로 이동 
     *  2. 이동이 완료된 파일의 정보를 리턴 
     *  3. 해당 파일 데이터와 팝업 데이터를 팝업 저장(/popup/save) request 데이터로 보낸다. 
     *  4. 팝업 저장 후 해당 팝업 아이디를 이용해 파일 데이터 저장
     * 
     *  xss : security에 필터를 적용하여 스크립트를 허용하지 못하도록 block한다.
     *  sql injection : mybatis에서 #{}를 사용하여 String으로 치환, 실행 하도록 한다.
     */
    @ResponseBody
    @PostMapping("/save")
    public PopupResponseDto save(@RequestBody @Valid PopupDto popupDto, Errors errors) throws Exception {

        PopupResponseDto resultDto = PopupResponseDto.builder().build();

        if(errors.hasErrors()){ // NotBlank 예외 처리
            resultDto = PopupResponseDto.createErrorResponse(popupDto, errors.getAllErrors().toString());
        }else if(popupDto.getPopupID() == 0) { // 추가
            resultDto = popupService.insertPopup(popupDto, resultDto, popupDto.getImageList());
        }else if(popupDto.getPopupID() > 0){ // popupDto
            resultDto = popupService.updatePopup(popupDto, resultDto, popupDto.getImageList());
        }else {
            resultDto = PopupResponseDto.createErrorResponse(popupDto, "잘못된 작업이 요청되었습니다.");
        }
        return resultDto;
    }

    /*
     *  @description : 팝업 수정폼
     *  팝업 ID를 가져와 해당 팝업 게시판을 가져와 해당 수정 폼으로 이동하는 기능입니다.
     */
    @GetMapping("/form/{popupID}")
    public String popupForm(PopupDto popupDto, Model model, @PathVariable("popupID") String popupStringID) {

        int popupID = 0;
        PopupResponseDto resultDto = PopupResponseDto.builder().build();

        try{
            popupID = Integer.parseInt(popupStringID);
        }catch(Exception e) {
            resultDto = PopupResponseDto.createErrorResponse(null, "잘못된 작업이 요청되었습니다.");
        }

        resultDto = popupService.selectPopup(popupID, resultDto);
        popupService.selectPopupForm(resultDto.getData(), model); // 조회 예외처리 및 result 값 세팅

        return "popup/popupForm.admin";
    }
    



    /*
     *  @description : 팝업 삭제(다중 삭제 포함)
     *  HttpServletRequest 안 단일 혹은 다중 popupID 값을 가져와 삭제(IsDelete='Y')시키는 기능입니다.
     */
    @ResponseBody
    @DeleteMapping("/delete")
    public PopupListResponseDto popupDelete(HttpServletRequest request) {

        PopupListResponseDto resultDto = PopupListResponseDto.builder().build();

        String[] paramArr = request.getParameterValues("popupID");

        if(!paramArr[0].isEmpty()){
            List<String> paramList = Arrays.asList(paramArr);
            List<Integer> intParamList = new ArrayList<>();

            //requestID를 int형으로 변환
            try {
                intParamList = paramList.stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
                resultDto = popupService.deletePopupList(intParamList, resultDto);
            }catch(Exception e) { // 문자열로 들어왔을 경우
                resultDto = PopupListResponseDto.createErrorResponse(intParamList, "잘못된 작업이 요청되었습니다.");
            }
            
        }else{
            resultDto = PopupListResponseDto.createErrorResponse(null, "잘못된 작업이 요청되었습니다.");
        }

        return resultDto;

    }

    /*
     *  @description : 팝업 표시 타입 목록 조회
     *  (쇼핑몰, 커뮤니티)
     *  
     */
    @GetMapping("/popupTypeList")
    public String popupTypeList(Model model) {

        List<PopupTypeDto> result = popupService.selectPopupTypeList();
        model.addAttribute("popupTypeList", result);
        
        return "popup/popupTypeList.admin";
    }

    /*
     *  @description : 팝업 표시 타입 저장
     *  
     */
    @ResponseBody
    @PostMapping("/popupType/save")
    public PopupTypeResponseDto popupTypeSave(@RequestBody @Valid PopupTypeDto popupTypeDto, Errors errors) {

        PopupTypeResponseDto resultDto = PopupTypeResponseDto.builder().build();

        if(errors.hasErrors()){ // NotBlank 예외 처리
            resultDto = PopupTypeResponseDto.createErrorResponse(popupTypeDto, errors.getAllErrors().toString());
        }else if(popupTypeDto.getPopupTypeID() == 0) { // 추가
            resultDto = popupService.insertPopupType(popupTypeDto, resultDto);
        }else if(popupTypeDto.getPopupTypeID() > 0){ // popupDto
            resultDto = popupService.updatePopupType(popupTypeDto, resultDto);
        }else {
            resultDto = PopupTypeResponseDto.createErrorResponse(popupTypeDto, "잘못된 작업이 요청되었습니다.");
        }
        return resultDto;

    }

    /*
     *  @description : 팝업 표시 타입 추가 폼
     *  
     */
    @GetMapping("/popupType/write")
    public String popupTypeWrite() {
        return "popup/popupTypeWrite.admin";
    }

    /*
     *  @description : 팝업 표시 타입 삭제(다중 삭제 포함)
     *  
     */
    @ResponseBody
    @DeleteMapping("/popupType/delete")
    public PopupTypeListResponseDto popupTypeDelete(HttpServletRequest request) {

        PopupTypeListResponseDto resultDto = PopupTypeListResponseDto.builder().build();

        String[] paramArr = request.getParameterValues("popupTypeID");

        if(!paramArr[0].isEmpty()){
            List<String> paramList = Arrays.asList(paramArr);
            List<Integer> intParamList = new ArrayList<>();

            //requestID를 int형으로 변환
            try {
                intParamList = paramList.stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
                resultDto = popupService.deletePopupTypeList(intParamList, resultDto);
            }catch(Exception e) { // 문자열로 들어왔을 경우
                resultDto = PopupTypeListResponseDto.createErrorResponse(null, "잘못된 작업이 요청되었습니다.");
            }
        }else{
            resultDto = PopupTypeListResponseDto.createErrorResponse(null, "잘못된 작업이 요청되었습니다.");
        }

        return resultDto;
    }


    /*
     *  @description : 팝업 표시 기기 타입 목록 조회
     *  (모바일, PC)
     *  
     */
    @GetMapping("/popupConnectTypeList")
    public String popupConnectTypeList(Model model) {

        List<PopupConnectTypeDto> result = popupService.selectPopupConnectTypeList();
        model.addAttribute("popupConnectTypeList", result);
        
        return "popup/popupTypeList.admin";
    }

    /*
     *  @description : 팝업 표시 기기 타입 저장
     *  
     */
    @ResponseBody
    @PostMapping("/popupConnectType/save")
    public PopupConnectTypeResponseDto popupTypeSave(@RequestBody @Valid PopupConnectTypeDto popupConnectTypeDto, Errors errors) {

        PopupConnectTypeResponseDto resultDto = PopupConnectTypeResponseDto.builder().build();

        if(errors.hasErrors()){ // NotBlank 예외 처리
            resultDto = PopupConnectTypeResponseDto.createErrorResponse(popupConnectTypeDto, errors.getAllErrors().toString());
        }else if(popupConnectTypeDto.getPopupConnectTypeID() == 0) { // 추가
            resultDto = popupService.insertPopupConnectType(popupConnectTypeDto, resultDto);
        }else if(popupConnectTypeDto.getPopupConnectTypeID() > 0){ // 수정
            resultDto = popupService.updatePopupConnectType(popupConnectTypeDto, resultDto);
        }else {
            resultDto = PopupConnectTypeResponseDto.createErrorResponse(popupConnectTypeDto, "잘못된 작업이 요청되었습니다.");
        }
        return resultDto;

    }

    /*
     *  @description : 팝업 표시 기기 타입 삭제(다중 삭제 포함)
     *  
     */
    @ResponseBody
    @DeleteMapping("/popupConnectType/delete")
    public PopupConnectTypeListResponseDto popupConnectTypeDelete(HttpServletRequest request) {

        PopupConnectTypeListResponseDto resultDto = PopupConnectTypeListResponseDto.builder().build();

        String[] paramArr = request.getParameterValues("popupConnectTypeID");

        if(paramArr != null && !paramArr[0].isEmpty()){
            List<String> paramList = Arrays.asList(paramArr);
            List<Integer> intParamList = new ArrayList<>();

            //requestID를 int형으로 변환
            try {
                intParamList = paramList.stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
                resultDto = popupService.deletePopupConnectTypeList(intParamList, resultDto);
            }catch(Exception e) { // 문자열로 들어왔을 경우
                resultDto = PopupConnectTypeListResponseDto.createErrorResponse(null, "잘못된 작업이 요청되었습니다.");
            }
        }else {
            resultDto = PopupConnectTypeListResponseDto.createErrorResponse(null, "잘못된 작업이 요청되었습니다.");
        }

        return resultDto;
    }

    /*
     *  @description : 팝업 이미지 다중 저장
     *  팝업 이미지를 저장, 이동하는 기능입니다.
     */
    @ResponseBody
    @PostMapping("/image/save")
    public FileListResponseDto save(@RequestParam("multipartList") List<MultipartFile> multipartList, @RequestParam("path") String path, PopupDto dto) throws Exception {

        FileListResponseDto resultDto = FileListResponseDto.builder().build();

        // 최대 10개까지 이미지 업로드 가능
        if(multipartList.size() > 0 && multipartList.size() < 11){
            resultDto = fileService.createFile(multipartList, dto.getPopupID(), path);
        }else if(multipartList.size() > 10) {
            resultDto = FileListResponseDto.createErrorResponse(null, null, "파일은 10개까지 업로드 가능합니다.");
        }else {
            resultDto = FileListResponseDto.createErrorResponse(null, null, "잘못된 요청입니다.");
        }

        return resultDto;
    }

    @GetMapping("/injectoinTestForm")
    public String injectoinTestForm() throws Exception {
        return "popup/injectionTestForm";
        
    }

    @PostMapping("/injection.do")
    public String postMethodName(Model model, @RequestParam("test") String test) {
        
        List<PopupDto> dto = popupService.injectionTest(test);
        model.addAttribute("test", dto);
        return "popup/injection.do.admin";
    }

    @GetMapping("/imageForm")
    public String imageForm() {
        return "popup/popupImageWrite.admin";
    }
    
    
    
}
