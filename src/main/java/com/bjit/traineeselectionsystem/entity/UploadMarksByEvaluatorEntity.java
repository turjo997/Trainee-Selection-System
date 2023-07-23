package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "upload_marks_evaluator")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadMarksByEvaluatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uploadId;

    @ManyToOne
    @JoinColumn(name = "evaluator_id")
    private EvaluatorEntity evaluator;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;

    @ManyToOne
    @JoinColumn(name = "circular_id")
    private JobCircularEntity jobCircular;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private ExamCategoryEntity examCategory;

    @Column(nullable = false)
    private double marks;
}
