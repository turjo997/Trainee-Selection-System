package com.bjit.traineeselectionsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailRequest {
    private Long userId;
    private Long circularId;
    private Long examId;
    private Long applicantId;
//    private String subject;
//    private String body;
}
