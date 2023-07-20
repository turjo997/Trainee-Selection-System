package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "job_circulars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCircularEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long circularId;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;


    private String circularTitle;


    private String jobType;


    private LocalDate openDate;


    private LocalDate closeDate;


    private String jobDescription;


    private String status;
}
