package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.entity.AdmitCardEntity;
import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.model.AdmitCardRequest;
import com.bjit.traineeselectionsystem.model.AdmitCardResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface AdmitCardService {


    ResponseEntity<String> generateAdmitCards(AdmitCardRequest admitCardRequest);

    void export(HttpServletResponse response , Long applicantId) throws IOException;

    //byte[] generateAdmitCardPDF(AdmitCardEntity admitCardEntity) throws IOException;

    AdmitCardEntity getAdmitCardByApplicantId(Long applicantId);

  //  AdmitCardEntity getAdmitCardByApplicantId(Long applicantId);
}
