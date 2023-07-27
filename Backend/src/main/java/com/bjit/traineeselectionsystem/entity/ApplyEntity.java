package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;

    @ManyToOne
    @JoinColumn(name = "circular_id")
    private JobCircularEntity jobCircular;

}
