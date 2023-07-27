package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.ExamCreateServiceException;
import com.bjit.traineeselectionsystem.exception.JobCircularServiceException;
import com.bjit.traineeselectionsystem.service.CodeGeneratorService;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QRCodeGenerator implements CodeGeneratorService {

    private final RepositoryManager repositoryManager;


    private static String QRCODE_PATH = "E:\\BJIT Final Project\\TSS-Server\\YSD_B02_J2EE_FinalProject_Ullash\\Backend\\QR_images\\";
    //"E:\\BJIT Final Project\\YSD_B02_J2EE_FinalProject_Ullash\\QR_images\\";
    //"C:\\Users\\BJIT\\Desktop\\New folder\\trainee-selection-system\\QR_images\\";

    public ResponseEntity<String> writeQRCode(Long circularId) throws Exception {

        Long examId = 1l;

        try {
            repositoryManager.getJobCircularRepository().findById(circularId).orElseThrow(() -> new JobCircularServiceException("Circular not found"));


            repositoryManager.getExamCreateRepository().findById(examId).orElseThrow(() -> new ExamCreateServiceException("Exam not found"));


            // Get the approved applicants for a specific circular and exam
            List<ApproveEntity> approvedApplicants = repositoryManager.getApproveRepository().findByJobCircularCircularIdAndExamCategoryExamId(circularId, examId);


            for (ApproveEntity approve : approvedApplicants) {
                String qrcode = QRCODE_PATH + approve.getApplicant().getApplicantId() + "_" + approve.getApplicant().getFirstName() + "_" + approve.getApplicant().getLastName() + "-QRCODE.png";

                String contents = "Id: " + approve.getApplicant().getApplicantId() + "\n" + "Name : " + approve.getApplicant().getFirstName() + " " + approve.getApplicant().getLastName() + "\n" + "Address: " + approve.getApplicant().getAddress() + "\n" + "Date of Birth : " + approve.getApplicant().getDob() + "\n" + "Gender : " + approve.getApplicant().getGender();

                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, 350, 350);

                Path path = FileSystems.getDefault().getPath(qrcode);
                MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            }

            return ResponseEntity.ok("QRCODE is generated successfully");
        } catch (JobCircularServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ExamCreateServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
