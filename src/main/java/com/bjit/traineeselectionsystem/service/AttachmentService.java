package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.entity.AttachmentEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    AttachmentEntity saveAttachment(MultipartFile file , Long userId) throws Exception;

    AttachmentEntity getAttachment(String fileId) throws Exception;
}
