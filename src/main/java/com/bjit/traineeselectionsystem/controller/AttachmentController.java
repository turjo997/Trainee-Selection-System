package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.entity.AttachmentEntity;
import com.bjit.traineeselectionsystem.model.ResponseData;
import com.bjit.traineeselectionsystem.service.AttachmentService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@CrossOrigin
@RestController

public class AttachmentController {
    private AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/applicant/upload/{userId}")
    public ResponseData uploadFile(@RequestParam("image") MultipartFile image ,
                                   @RequestParam("cv") MultipartFile cv , @PathVariable Long userId) throws Exception {

        AttachmentEntity imageAttachment = attachmentService.saveAttachment(image , userId);
        AttachmentEntity cvAttachment = attachmentService.saveAttachment(cv , userId);

        String imageDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("applicant/download/")
                .path(imageAttachment.getId())
                .toUriString();

        String cvDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(cvAttachment.getId())
                .toUriString();

        return new ResponseData(imageAttachment.getFileName(),
                imageDownloadUrl,
                image.getContentType(),
                image.getSize(),
                cvAttachment.getFileName(),
                cvDownloadUrl,
                cv.getContentType(),
                cv.getSize());
    }

    @GetMapping("applicant/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        AttachmentEntity attachment = null;
        attachment = attachmentService.getAttachment(fileId);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName()
                                + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
}
