package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.*;
import com.bjit.traineeselectionsystem.model.AdmitCardRequest;
import com.bjit.traineeselectionsystem.service.AdmitCardService;
import com.bjit.traineeselectionsystem.service.AttachmentService;

import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdmitCardServiceImpl implements AdmitCardService {

    private final AttachmentService attachmentService;
    private final RepositoryManager repositoryManager;


    @Override
    public ResponseEntity<String> generateAdmitCards(AdmitCardRequest admitCardRequest) {

        try {
            AdminEntity adminEntity = repositoryManager.getAdminRepository().findById(admitCardRequest.getAdminId())
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(admitCardRequest.getCircularId())
                    .orElseThrow(() -> new JobCircularServiceException("Circular not found"));

            ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository().findById(admitCardRequest.getExamId())
                    .orElseThrow(() -> new ExamCreateServiceException("Exam not found"));

            // Get the approved applicants for a specific circular and exam
            List<ApproveEntity> selectedApplicants = repositoryManager.getApproveRepository().findByJobCircularCircularIdAndExamCategoryExamId(admitCardRequest.getCircularId(), admitCardRequest.getExamId());

            List<AdmitCardEntity> addAdmitCard = new ArrayList<>();

            for (ApproveEntity approve : selectedApplicants) {
                AdmitCardEntity admitCardEntity = AdmitCardEntity.builder()
                        .admin(adminEntity)
                        .applicant(approve.getApplicant())
                        .jobCircular(jobCircularEntity)
                        .instructions(admitCardRequest.getInstructions())
                        .examDate(admitCardRequest.getExamDate())
                        .examTime(admitCardRequest.getExamTime())
                        .examVenue(admitCardRequest.getExamVenue())
                        .build();
                addAdmitCard.add(admitCardEntity);
            }

            // Save the generated AdmitCardEntity to the database or perform any other necessary actions
            repositoryManager.getAdmitCardRepository().saveAll(addAdmitCard);

            return ResponseEntity.ok("admit card generated successfully");
        } catch (AdminServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JobCircularServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ExamCreateServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Log the error or handle it as per your application's requirements
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public AdmitCardEntity getAdmitCardByApplicantId(Long userId) {
        try {

            UserEntity user = repositoryManager.getUserRepository().findById(userId)
                    .orElseThrow(()->new UserServiceException("User not found"));

            ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository().findByUser(user)
                    .orElseThrow(() -> new IllegalArgumentException("Applicant not found"));

            Optional<AdmitCardEntity> admitCardOptional = repositoryManager.getAdmitCardRepository().findByApplicant(applicantEntity);

            return admitCardOptional.orElseThrow(() -> new EntityNotFoundException("AdmitCard not found for the given applicant"));
        }catch (UserServiceException e){
            throw new EntityNotFoundException(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            // Log the error or handle it as per your application's requirements
            throw new EntityNotFoundException(e.getMessage());
        }
    }


    @Override
    public void export(HttpServletResponse response, Long userId) throws IOException {
        try {

            UserEntity user = repositoryManager.getUserRepository().findById(userId)
                    .orElseThrow(()->new UserServiceException("User not found"));


            ApplicantEntity applicantEntity = repositoryManager.getApplicantRepository().findByUser(user)
                    .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

            AdmitCardEntity admitCardEntity = repositoryManager.getAdmitCardRepository().findByApplicant(applicantEntity)
                    .orElseThrow(() -> new EntityNotFoundException("Admit Card not found for the given applicant"));

            // Generate QR code filename based on the applicant
            String qrCodeFilename = applicantEntity.getApplicantId() + "_" + applicantEntity.getFirstName() + "_" +
                    applicantEntity.getLastName() + "-QRCODE.png";

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTitle.setSize(22);

            // Add "Admit Card" at the beginning
            Paragraph admitCardTitle = new Paragraph("Admit Card", fontTitle);
            admitCardTitle.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(admitCardTitle);

            Font fontSubtitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontSubtitle.setSize(18);

            // Add "Written Exam" at the beginning
            Paragraph examType = new Paragraph("Written Exam", fontSubtitle);
            examType.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(examType);

            Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
            fontParagraph.setSize(15);

            // Get details from the AdmitCardEntity and include them in the PDF
            Long Id = applicantEntity.getApplicantId();
            String applicantName = applicantEntity.getFirstName() + " " + applicantEntity.getLastName();
            String examDate = admitCardEntity.getExamDate().toString();
            String examTime = admitCardEntity.getExamTime().toString();
            String examVenue = admitCardEntity.getExamVenue();
            //String instructions = admitCardEntity.getInstructions();

            String instructions = "1. Each candidate must bring printed copy of this admit card into the exam hall"
                    + "\n" + "2. Candidate should be present in the concerned center 30(thirty) minute before the exam starts" +
                    "\n" + "3. Carrying any kind of electronic device like mobile is strongly prohibited.";

            String circular = admitCardEntity.getJobCircular().getCircularTitle();
            // Add more details as needed


            Paragraph paragraph1 = new Paragraph("Applicant ID           : " + Id, fontParagraph);

            Paragraph paragraph2 = new Paragraph("Applicant Name      : " + applicantName, fontParagraph);

            Paragraph paragraph3 = new Paragraph("Circular                  : " + circular, fontParagraph);

            // Add more details as needed

            Paragraph paragraph4 = new Paragraph("Exam Date             : " + examDate, fontParagraph);

            Paragraph paragraph5 = new Paragraph("Exam Time             : " + examTime, fontParagraph);

            Paragraph paragraph6 = new Paragraph("Exam Venue           : " + examVenue, fontParagraph);

            Paragraph paragraph7 = new Paragraph("General Instructions  : ", fontParagraph);

            Paragraph paragraph8 = new Paragraph(instructions, fontParagraph);

            //Paragraph paragraph1 = new Paragraph("Applicant ID        : " + Id, fontParagraph);
            paragraph1.setAlignment(Paragraph.ALIGN_LEFT);


            //Paragraph paragraph2 = new Paragraph("Applicant Name        : " + applicantName, fontParagraph);
            paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

            //Paragraph paragraph3 = new Paragraph("Circular        : " + circular, fontParagraph);
            paragraph3.setAlignment(Paragraph.ALIGN_LEFT);

            //Paragraph paragraph4 = new Paragraph("Exam Date        : " + examDate, fontParagraph);
            paragraph4.setAlignment(Paragraph.ALIGN_LEFT);

            //Paragraph paragraph5 = new Paragraph("Exam Time        : " + examTime, fontParagraph);
            paragraph5.setAlignment(Paragraph.ALIGN_LEFT);

            //Paragraph paragraph6 = new Paragraph("Exam Venue        : " + examVenue, fontParagraph);
            paragraph6.setAlignment(Paragraph.ALIGN_LEFT);

            //Paragraph paragraph7 = new Paragraph("General Instructions:" , fontParagraph);
            paragraph7.setAlignment(Paragraph.ALIGN_LEFT);

            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
            document.add(paragraph6);

            String QRCODE_PATH = "E:\\BJIT Final Project\\TSS-Server\\YSD_B02_J2EE_FinalProject_Ullash\\Backend\\QR_images\\";

            String qrCodePath = QRCODE_PATH + qrCodeFilename;

            try (InputStream qrCodeStream = new FileInputStream(qrCodePath)) {
                Image qrCodeImage = Image.getInstance(IOUtils.toByteArray(qrCodeStream));
                qrCodeImage.setAlignment(Element.ALIGN_CENTER);
                qrCodeImage.scaleAbsolute(100, 100);
                document.add(qrCodeImage);
            } catch (IOException | BadElementException e) {
                // Handle any exceptions that might occur while loading or adding the QR code image
                e.printStackTrace();
            }

            document.add(paragraph7);
            document.add(paragraph8);

            document.close();

        } catch (ApplicantServiceException e) {
            throw new ApplicantServiceException(e.getMessage());
        } catch (EntityNotFoundException e) {
            // Log the error or handle it as per your application's requirements
            throw new EntityNotFoundException(e.getMessage());
        }

    }

}