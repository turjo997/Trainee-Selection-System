package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mailing_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailingStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mailingId;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, length = 2000)
    private String body;

    @Column(nullable = false)
    private String fromEmail;

    @Column(nullable = false)
    private String toEmail;
}
