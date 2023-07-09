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

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false)
    private String contact;

    private String degreeName;

    private String institute;

    private Double cgpa;

    private Integer passingYear;

    private String address;

    @Lob
    @Column(name = "image_file")
    private byte[] imageFile;

    @Lob
    @Column(name = "cv_file")
    private byte[] cvFile;

//    @Column(name = "image_file")
//    private String imageFile;
//
//    @Column(name = "cv_file")
//    private String cvFile;
}