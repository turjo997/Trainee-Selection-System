package com.bjit.traineeselectionsystem.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CircularCreateRequest {
    private Long circularId;
    private Long userId;
    private String circularTitle;
    private String jobType;
    private LocalDate openDate;
    private LocalDate closeDate;
    private String jobDescription;
    private String status;
}
