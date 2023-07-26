package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "applicants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicantId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String firstName;

    private String lastName;

    private String gender;

    private LocalDate dob;

    private String contact;

    private String degreeName;

    private String institute;

    private Double cgpa;

    private Integer passingYear;

    private String address;

}
