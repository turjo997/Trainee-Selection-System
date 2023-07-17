package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.entity.AdmitCardEntity;
import com.bjit.traineeselectionsystem.repository.AdmitCardRepository;
import com.bjit.traineeselectionsystem.repository.ApplicantRepository;
import com.bjit.traineeselectionsystem.service.AdmitCardService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class AdmitCardController {

    private final AdmitCardService admitCardService;

    public AdmitCardController(AdmitCardService admitCardService) {
        this.admitCardService = admitCardService;
    }


    @GetMapping("/download/{applicantId}")
    public void generatePDF(HttpServletResponse response , @PathVariable Long applicantId) throws IOException {

        AdmitCardEntity admitCardEntity = admitCardService.getAdmitCardByApplicantId(applicantId);

        if (admitCardEntity == null) {
            throw new EntityNotFoundException("AdmitCard not found for the given applicant");
        }


        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.admitCardService.export(response , applicantId);
    }















//    @GetMapping("/download/{applicantId}")
//    public ResponseEntity<byte[]> downloadAdmitCard(@PathVariable Long applicantId) throws IOException {
//        byte[] pdfData = null;
//        HttpHeaders headers = new HttpHeaders();
//
//        try {
//            AdmitCardEntity admitCardEntity = admitCardService.getAdmitCardByApplicantId(applicantId);
//
//            if (admitCardEntity == null) {
//                throw new EntityNotFoundException("AdmitCard not found for the given applicant");
//            }
//            pdfData = admitCardService.generateAdmitCardPDF(admitCardEntity);
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentDispositionFormData("attachment", "AdmitCard.pdf");
//
//        } catch (IOException e) {
//            // Handle any IO exceptions that may occur during PDF generation
//            e.printStackTrace();
//            // You can also throw a custom exception if needed
//            // throw new SomeCustomException("Error generating PDF for Admit Card");
//        }
//
//        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
//    }

}








