package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approval")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approveId;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;

    @ManyToOne
    @JoinColumn(name = "circular_id")
    private JobCircularEntity jobCircular;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private ExamCategoryEntity examCategory;


    private boolean approve;
}
