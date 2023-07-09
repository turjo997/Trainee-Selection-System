package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "evaluators")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evaluatorId;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;

    @Column(nullable = false)
    private String evaluatorName;

    @Column(nullable = false, unique = true)
    private String evaluatorEmail;

    private String designation;

    private String contactNumber;

    private String qualification;

    private String specialization;

    private boolean active;
}
