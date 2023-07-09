package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "admit_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmitCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admitId;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;


    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;

    @Column(nullable = false)
    private String barCode;

    @Column(nullable = false)
    private String qrCode;

    @Column(length = 1000)
    private String instructions;

    private LocalDate examDate;

    private LocalTime examTime;

    private String examVenue;
}
