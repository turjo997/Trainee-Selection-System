package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.AdminEntity;
import com.bjit.traineeselectionsystem.entity.ApplicantEntity;
import com.bjit.traineeselectionsystem.entity.ImageEntity;
import com.bjit.traineeselectionsystem.entity.UserEntity;
import com.bjit.traineeselectionsystem.model.ApplicantCreateRequest;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.repository.AdminRepository;
import com.bjit.traineeselectionsystem.repository.ApplicantRepository;
import com.bjit.traineeselectionsystem.repository.ImageRepository;
import com.bjit.traineeselectionsystem.repository.UserRepository;
import com.bjit.traineeselectionsystem.service.UserService;
import com.bjit.traineeselectionsystem.utils.HashingPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ApplicantRepository applicantRepository;
    private final ImageRepository imageRepository;
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
    public void addApplicant(ApplicantCreateRequest applicantCreateRequest) throws IOException {

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
                .build();

        // Save the applicant to the ApplicantRepository
        ApplicantEntity savedApplicant = applicantRepository.save(applicant);

        ImageEntity image = ImageEntity.builder()
                .applicant(savedApplicant)
                .imageFileName(applicantCreateRequest.getImageFile().getOriginalFilename())
                .imageFileType(applicantCreateRequest.getImageFile().getContentType())
                .imageFile(applicantCreateRequest.getImageFile().getBytes())
                .build();

        imageRepository.save(image);


    }



}
