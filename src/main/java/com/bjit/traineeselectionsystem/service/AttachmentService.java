package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.entity.AttachmentEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    AttachmentEntity saveAttachment(MultipartFile file) throws Exception;

    AttachmentEntity getAttachment(String fileId) throws Exception;
}
