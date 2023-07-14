package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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

//    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    private String id;


    @OneToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;


//    private String fileName;
//    private String fileType;
//
//    @Lob
//    @Column(columnDefinition = "LONGBLOB")
//    private byte[] data;

//    public QRCodeEntity(ApplicantEntity applicant , String fileName, String fileType, byte[] data) {
//        this.applicant = applicant;
//        this.fileName = fileName;
//        this.fileType = fileType;
//        this.data = data;
//    }
}
