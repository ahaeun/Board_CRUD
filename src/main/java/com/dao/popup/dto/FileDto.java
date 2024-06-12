package com.dao.popup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private int fileID;
    private String fileOriginalName;
    private String fileEncryptName;
    private String fileSize;
    private String fileMimetype;
    private String fileExtension;
    private String filePath;

}
