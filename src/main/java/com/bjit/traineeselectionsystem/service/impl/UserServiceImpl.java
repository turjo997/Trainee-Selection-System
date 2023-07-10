package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.AdminEntity;
import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.entity.UserEntity;
import com.bjit.traineeselectionsystem.model.ApplicantCreateRequest;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.model.UserCreateRequest;
import com.bjit.traineeselectionsystem.repository.AdminRepository;
import com.bjit.traineeselectionsystem.repository.ApplicantRepository;
import com.bjit.traineeselectionsystem.repository.UserRepository;
import com.bjit.traineeselectionsystem.service.UserService;
import com.bjit.traineeselectionsystem.util.HashingPassword;
import com.bjit.traineeselectionsystem.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ApplicantRepository applicantRepository;
    @Override
    public void addAdmin() {

        String email = "admin@gmail.com";
        String password = HashingPassword.hashPass("admin12345");
        String role = "ADMIN";

        if (userRepository.existsByEmail(email)) {
            String errorMessage = "Admin with the same email already exists";
            System.out.println(errorMessage);
        }else{
            UserEntity userEntity = UserEntity.builder()
                    .email(email)
                    .password(password)
                    .role(role)
                    .build();
            // Save the user to the UserRepository
            UserEntity savedUser = userRepository.save(userEntity);


            // Create a new AdminEntity with the saved user
            AdminEntity admin = new AdminEntity();
            admin.setUser(savedUser);
            // Set other admin details

            // Save the admin to the AdminRepository
            AdminEntity savedAdmin = adminRepository.save(admin);

            // Create a response with the saved admin
            Response<AdminEntity> response = new Response<>();
            response.setData(savedAdmin);

            System.out.println("Account created");
            //return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Override
    public void addApplicant(MultipartFile file1 , ApplicantCreateRequest applicantCreateRequest) throws IOException {

        String role = "Applicant";

        // Create a new UserEntity
        UserEntity user = UserEntity.builder()
                .email(applicantCreateRequest.getEmail())
                .password(applicantCreateRequest.getPassword())
                .role(role)
                .build();

        // Save the user to the UserRepository
        UserEntity savedUser = userRepository.save(user);

        // Create a new ApplicantEntity with the saved user
        ApplicantEntity applicant = ApplicantEntity.builder()
                .user(savedUser)
                .firstName(applicantCreateRequest.getFirstName())
                .lastName(applicantCreateRequest.getLastName())
                .gender(applicantCreateRequest.getGender())
                .dob(applicantCreateRequest.getDob())
                .contact(applicantCreateRequest.getContact())
                .degreeName(applicantCreateRequest.getDegreeName())
                .institute(applicantCreateRequest.getInstitute())
                .cgpa(applicantCreateRequest.getCgpa())
                .passingYear(applicantCreateRequest.getPassingYear())
                .address(applicantCreateRequest.getAddress())
                .imageFileName(file1.getOriginalFilename())
                .imageFileType(file1.getContentType())
                .imageFile(ImageUtils.compressImage(file1.getBytes()))
                //.pdfFileName(file2.getOriginalFilename())
                //.pdfFileType(file2.getContentType())
               // .cvFile(ImageUtils.compressImage(file2.getBytes()))
                .build();

        // Save the applicant to the ApplicantRepository
        ApplicantEntity savedApplicant = applicantRepository.save(applicant);

        // Create a response with the saved applicant
//        Response<ApplicantEntity> response = new Response<>();
//        response.setData(savedApplicant);

        //return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
