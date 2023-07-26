package com.bjit.traineeselectionsystem.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "upload_marks_admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadMarksByAdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uploadId;

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

    @Column(nullable = false)
    private double marks;

}
