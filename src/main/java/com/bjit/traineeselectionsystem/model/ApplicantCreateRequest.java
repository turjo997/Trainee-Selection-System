package com.bjit.traineeselectionsystem.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicantCreateRequest {

    private String email;
    private String password;
    private String role;

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
    //private String imageFileName;
    //private String imageFileType;
    //private byte[] imageFile;
    private MultipartFile imageFile;

   // private byte[] cvFile;

   // private MultipartFile imageFile;
   // private MultipartFile cvFile;
}
