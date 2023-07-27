package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor

public class AttachmentEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;

    private String fileName;
    private String fileType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    public AttachmentEntity(ApplicantEntity applicant , String fileName, String fileType, byte[] data) {
        this.applicant = applicant;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
