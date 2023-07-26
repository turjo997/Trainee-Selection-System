package com.bjit.traineeselectionsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmitCardRequest {

    private Long adminId ;
    private Long circularId;
    private Long examId;

    private String instructions;

    private LocalDate examDate;

    private LocalTime examTime;

    private String examVenue;
}
