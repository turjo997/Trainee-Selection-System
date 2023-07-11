package com.bjit.traineeselectionsystem.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @OneToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;

    private String imageFileType;

    private String imageFileName;

    @Lob
    @Column(name = "image_file"  ,length = 2000)
    private byte[] imageFile;
}
