package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "qr_code")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QRCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qrCodeId;

    @OneToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;

}
