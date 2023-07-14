package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.repository.*;
import com.bjit.traineeselectionsystem.service.CodeGeneratorService;
import com.bjit.traineeselectionsystem.utils.UniqueCode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QRCodeGenerator implements CodeGeneratorService {

    private final ApproveRepository approveRepository;
    private final ExamTrackRepository examTrackRepository;
    private final AdminRepository adminRepository;
    private final JobCircularRepository jobCircularRepository;
    private final ExamCreateRepository examCreateRepository;
    private final ApplicantRepository applicantRepository;
    private static String QRCODE_PATH = "C:\\Users\\BJIT\\Desktop\\New folder\\trainee-selection-system\\QR_images\\";

    public String writeQRCode(Long circularId, Long examId) throws Exception {

        JobCircularEntity jobCircularEntity = jobCircularRepository.findById(circularId)
                .orElseThrow(()-> new IllegalArgumentException("Circular not found"));


        ExamCategoryEntity examCategoryEntity = examCreateRepository.findById(examId)
                .orElseThrow(()->new IllegalArgumentException("Exam not found"));


        // Get the approved applicants for a specific circular and exam
        List<ApproveEntity> approvedApplicants = approveRepository.findByJobCircularCircularIdAndExamCategoryExamId(circularId, examId);


        for (ApproveEntity approve : approvedApplicants) {
            String qrcode = QRCODE_PATH + approve.getApplicant().getApplicantId()+"_"+ approve.getApplicant().getFirstName()+
                    "_"+approve.getApplicant().getLastName()+"-QRCODE.png";

            String contents = approve.getApplicant().getApplicantId() + ","
                    + approve.getApplicant().getFirstName() + ","
                    + approve.getApplicant().getLastName() + ","
                    + approve.getApplicant().getAddress() + ","
                    + approve.getApplicant().getDob() + ","
                    + approve.getApplicant().getGender();

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, 350, 350);

            Path path = FileSystems.getDefault().getPath(qrcode);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);


        }

        return "QRCODE is generated successfully....";
    }

}
