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
public class ApplyRequest {

    private Long userId;

    private Long circularId;
}
