package com.bjit.traineeselectionsystem.controller;

import com.bjit.traineeselectionsystem.entity.AdmitCardEntity;
import com.bjit.traineeselectionsystem.service.AdmitCardService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RestController
public class AdmitCardController {

    private final AdmitCardService admitCardService;

    public AdmitCardController(AdmitCardService admitCardService) {
        this.admitCardService = admitCardService;
    }


    @GetMapping("/download/{userId}")
    public void generatePDF(HttpServletResponse response , @PathVariable Long userId) throws IOException {

        AdmitCardEntity admitCardEntity = admitCardService.getAdmitCardByApplicantId(userId);

        if (admitCardEntity == null) {
            throw new EntityNotFoundException("AdmitCard not found for the given applicant");
        }


        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.admitCardService.export(response , userId);
    }
    
}








