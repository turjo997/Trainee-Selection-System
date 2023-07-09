package com.bjit.traineeselectionsystem.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "final_trainees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class FinalTraineesList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long traineesId;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;

    @ManyToOne
    @JoinColumn(name = "circular_id")
    private JobCircularEntity jobCircular;
}
