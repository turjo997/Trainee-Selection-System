package com.bjit.traineeselectionsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private String imageFileName;
    private String imageDownloadURL;
    private String imageFileType;
    private long imageFileSize;
    private String cvFileName;
    private String cvDownloadURL;
    private String cvFileType;
    private long cvFileSize;
}
