package com.bjit.traineeselectionsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @OneToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;

    private String fileType;

    private String fileName;

    @Lob
    @Column(name = "cv_file"  ,length = 2000)
    private byte[] cvFile;
}
