package com.dao.popup.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dao.popup.dto.response.FileListResponseDto;

public interface FileService {
    FileListResponseDto createFile(List<MultipartFile> multipartList, String path) throws Exception ;

    Map<String, List<MultipartFile>> fileImageCheck(List<MultipartFile> multipartList);
} 