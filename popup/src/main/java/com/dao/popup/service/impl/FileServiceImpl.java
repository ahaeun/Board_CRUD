package com.dao.popup.service.impl;

import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dao.popup.dto.FileDto;
import com.dao.popup.dto.response.FileListResponseDto;
import com.dao.popup.enums.BasicResponseData;
import com.dao.popup.mapper.FileMapper;
import com.dao.popup.security.Seed128;
import com.dao.popup.service.FileService;
import com.dao.popup.util.Path;
import com.dao.popup.util.Util;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    FileMapper fileMapper;

    @Autowired
    Seed128 seed;

    @Autowired
    Util util;


    @Override
    public FileListResponseDto createFile(List<MultipartFile> multipartList, int id, String path) throws Exception {

        seed.seedKey(Util.SEEDKEY);

        List<FileDto> successFileList = new ArrayList<>();
        List<FileDto> failFileList = new ArrayList<>();
        FileDto fileDto = FileDto.builder().build();
        FileListResponseDto resultDto = FileListResponseDto.builder().build();

        // 이미지 체크
        Map<String, List<MultipartFile>> checkMap = fileImageCheck(multipartList);

        // 실패 건 리스트에 담기
        for(MultipartFile image : checkMap.get("failFileList")) {
            fileDto = FileDto.builder()
                                .fileOriginalName(image.getOriginalFilename())
                                .build();
            failFileList.add(fileDto);
        }

        // 파일 이름과 확장자 분리
        for(MultipartFile image : checkMap.get("successFileList")) {
            String fileUrl = Path.IMAGE_URL + path; //표시되는 이미지 URL 경로
            String fileExtension = util.getExtensionByStringHandling(image.getOriginalFilename()).get(); //확장자
            String fileName = util.getImageNameByStringHandling(image.getOriginalFilename()).get(); //이미지 이름
            String filePath = Path.POPUP_IMAGE_PATH + path; //서버에 저장되는 폴더 경로

            // 파일 이름 암호화
            String enc = seed.encrypt(fileName);
            String encFile = enc + "." + fileExtension;

            // 폴더 유무 검사
            File newFolder = new File(filePath);

            if(!newFolder.exists()){
                try{
                    newFolder.mkdirs();
                }catch(Exception e){
                    e.getStackTrace();
                }
            }

            fileDto = FileDto.builder()
                                .fileOriginalName(image.getOriginalFilename())
                                .fileEncryptName(enc)
                                .fileExtension(fileExtension)
                                .fileMimetype(image.getContentType())
                                .fileSize(NumberFormat.getInstance().format(image.getSize()))
                                .filePath(fileUrl + "/" + encFile)
                                .build();
            try{             
                // 파일 이동
                image.transferTo(new File(filePath, encFile));
                successFileList.add(fileDto);
            }catch(Exception e) {
                failFileList.add(fileDto);
            }
        
        }

        if(multipartList.size() == successFileList.size()){
            resultDto = FileListResponseDto.createSuccessResponse(successFileList, BasicResponseData.SUCCESS.getMessage());
        }else {
            resultDto = FileListResponseDto.createErrorResponse(successFileList, failFileList, "저장에 실패한 이미지가 있습니다.");
        }
        
        
        return resultDto;
    }


    @Override
    public Map<String, List<MultipartFile>> fileImageCheck(List<MultipartFile> multipartList) {
        // 이미지 체크
        List<String> extensions = Util.IMAGE_EXTENSIONS;
        Map<String, List<MultipartFile>> resultMap = new HashMap<>();
        List<MultipartFile> successFileList = new ArrayList<>();
        List<MultipartFile> failFileList = new ArrayList<>();

        for(MultipartFile image : multipartList) {
            String fileExtension = util.getExtensionByStringHandling(image.getOriginalFilename()).get(); //확장자
            if(!extensions.contains(fileExtension)){
                failFileList.add(image);
            }else {
                successFileList.add(image);
            }
        }

        resultMap.put("successFileList", successFileList);
        resultMap.put("failFileList", failFileList);

        return resultMap;
    }
    
}
