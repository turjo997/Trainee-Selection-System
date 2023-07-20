package com.bjit.traineeselectionsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicantResponseModel {
    private Long applicantId;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dob;
    private String contact;
    private String degreeName;
    private String institute;
    private Double cgpa;
    private Integer passingYear;
    private String address;

}
