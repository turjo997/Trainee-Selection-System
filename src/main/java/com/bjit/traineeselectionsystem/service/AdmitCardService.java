package com.bjit.traineeselectionsystem.service;

import com.bjit.traineeselectionsystem.entity.AdmitCardEntity;
import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.model.AdmitCardRequest;
import com.bjit.traineeselectionsystem.model.AdmitCardResponse;

import java.util.List;

public interface AdmitCardService {


    void generateAdmitCards(AdmitCardRequest admitCardRequest);
  //  AdmitCardEntity getAdmitCardByApplicantId(Long applicantId);
}
