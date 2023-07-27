package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.ApplicantServiceException;
import com.bjit.traineeselectionsystem.exception.UserServiceException;
import com.bjit.traineeselectionsystem.repository.ApplicantRepository;
import com.bjit.traineeselectionsystem.repository.AttachmentRepository;
import com.bjit.traineeselectionsystem.repository.UserRepository;
import com.bjit.traineeselectionsystem.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {


    private final AttachmentRepository attachmentRepository;
    private final UserRepository userRepository;
    private final ApplicantRepository applicantRepository;

    @Override
    public AttachmentEntity saveAttachment(MultipartFile file, Long userId) throws Exception {

        // Long userId = 10L;

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException("User not found"));


        ApplicantEntity applicantEntity = applicantRepository.findByUser(user)
                .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));


        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence "
                        + fileName);
            }

            AttachmentEntity attachment
                    = new AttachmentEntity(applicantEntity, fileName,
                    file.getContentType(),
                    file.getBytes());

            return attachmentRepository.save(attachment);

        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Override
    public AttachmentEntity getAttachment(String fileId) throws Exception {
        return attachmentRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }
}
