package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "evaluations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evaluationId;

    @ManyToOne
    @JoinColumn(name = "evaluator_id")
    private EvaluatorEntity evaluator;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;

    @ManyToOne
    @JoinColumn(name = "circular_id")
    private JobCircularEntity jobCircular;

    @Column(nullable = false)
    private double totalMarks;
}
