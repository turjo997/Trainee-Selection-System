package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.UserServiceException;
import com.bjit.traineeselectionsystem.model.ApplicantCreateModel;
import com.bjit.traineeselectionsystem.model.AuthenticationResponse;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.repository.AdminRepository;
import com.bjit.traineeselectionsystem.repository.ApplicantRepository;
import com.bjit.traineeselectionsystem.repository.UserRepository;
import com.bjit.traineeselectionsystem.service.UserService;
import com.bjit.traineeselectionsystem.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ApplicantRepository applicantRepository;

    @Override
    public ResponseEntity<Object> addAdmin() {

        String email = "admin123@gmail.com";
        //String password = HashingPassword.hashPass("admin12345");
        String password = "admin12345";

        if (userRepository.existsByEmail(email)) {
            String errorMessage = "Admin with the same email already exists";
            System.out.println(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } else {
            UserEntity userEntity = UserEntity.builder().role(Role.ADMIN).email(email).password(passwordEncoder.encode(password)).build();


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

            AuthenticationResponse authRes = AuthenticationResponse.builder().token(jwtService.generateToken(savedUser)).build();

            System.out.println(authRes);
            return new ResponseEntity<>(authRes, HttpStatus.CREATED);
            //return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Object> addApplicant(ApplicantCreateModel applicantCreateRequest) {
        try {
            if (userRepository.existsByEmail(applicantCreateRequest.getEmail())) {
                String errorMessage = "Applicant with the same email already exists";
                throw new UserServiceException(errorMessage);
            } else {
                // Create a new UserEntity
                UserEntity user = UserEntity.builder().role(Role.APPLICANT).email(applicantCreateRequest.getEmail()).password(passwordEncoder.encode(applicantCreateRequest.getPassword())).build();

                // Save the user to the UserRepository
                UserEntity savedUser = userRepository.save(user);

                // Create a new ApplicantEntity with the saved user
                ApplicantEntity applicant = ApplicantEntity.builder().user(savedUser).firstName(applicantCreateRequest.getFirstName()).lastName(applicantCreateRequest.getLastName()).gender(applicantCreateRequest.getGender()).dob(applicantCreateRequest.getDob()).contact(applicantCreateRequest.getContact()).degreeName(applicantCreateRequest.getDegreeName()).institute(applicantCreateRequest.getInstitute()).cgpa(applicantCreateRequest.getCgpa()).passingYear(applicantCreateRequest.getPassingYear()).address(applicantCreateRequest.getAddress()).build();

                // Save the applicant to the ApplicantRepository
                applicantRepository.save(applicant);

                AuthenticationResponse authRes = AuthenticationResponse.builder().token(jwtService.generateToken(user)).build();
                return new ResponseEntity<>(authRes, HttpStatus.CREATED);
            }
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while adding the applicant", e);
        }

    }

}
