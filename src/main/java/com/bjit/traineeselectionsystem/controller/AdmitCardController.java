package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.entity.AdmitCardEntity;
import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.repository.AdmitCardRepository;
import com.bjit.traineeselectionsystem.repository.ApplicantRepository;
import com.bjit.traineeselectionsystem.service.AdmitCardService;
import com.bjit.traineeselectionsystem.service.impl.AdmitCardPdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/applicant")
@RequiredArgsConstructor
public class AdmitCardController {
    private final AdmitCardService admitCardService;
    private final AdmitCardRepository admitCardRepository;
    private final ApplicantRepository applicantRepository;



    @GetMapping("/admitCard/{applicantId}")
    public ResponseEntity<byte[]> downloadAdmitCard(@PathVariable Long applicantId) throws IOException {

        // Retrieve the applicant by applicantId from the applyRequest
        ApplicantEntity applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found"));


        // Get the AdmitCardEntity for the specified applicant ID
        AdmitCardEntity admitCard = admitCardRepository.findByApplicant(applicant);
                //admitCardService.getAdmitCardByApplicantId(applicantId);

        // Generate the Admit Card PDF
        byte[] pdfBytes = AdmitCardPdfGenerator.generateAdmitCardPdf(admitCard);

        // Set the HTTP headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "admitcard.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}








