package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.model.*;
import com.bjit.traineeselectionsystem.repository.*;
import com.bjit.traineeselectionsystem.service.AdminService;
import com.bjit.traineeselectionsystem.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final JobCircularRepository jobCircularRepository;
    private final EvaluatorRepository evaluatorRepository;
    private final ExamCreateRepository examCreateRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;



    @Override
    public ResponseEntity<Response<?>> createCircular(CircularCreateRequest circularCreateRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long loggedInApplicantId = ((UserEntity) authentication.getPrincipal()).getUserId(); // 23

        UserEntity user = userRepository.findById(loggedInApplicantId)
                .orElseThrow(()->new IllegalArgumentException("User not found"));

        //Applicant --> user(23) -- > email

        AdminEntity admin= adminRepository.findByUser(user);

        //System.out.println(loggedInApplicantId);

        // Check if the applicantId from the ApplyRequest matches the logged-in user's applicantId
        if (!circularCreateRequest.getAdminId().equals(admin.getAdminId())) {
            throw new IllegalArgumentException("Invalid admin ID");
            // or return an error response indicating that the applicant ID does not match the logged-in user
        }


        // Get the admin by adminId from the circularCreateRequest
        AdminEntity adminEntity = adminRepository.findById(circularCreateRequest.getAdminId())
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));

        // Create a new JobCircularEntity
        JobCircularEntity jobCircularEntity = JobCircularEntity
                .builder()
                .admin(adminEntity)
                .circularTitle(circularCreateRequest.getCircularTitle())
                .jobType(circularCreateRequest.getJobType())
                .openDate(circularCreateRequest.getOpenDate())
                .closeDate(circularCreateRequest.getCloseDate())
                .jobDescription(circularCreateRequest.getJobDescription())
                .status(circularCreateRequest.getStatus())
                .build();

        // Save the job circular to the repository
        JobCircularEntity savedJobCircular = jobCircularRepository.save(jobCircularEntity);

        // Create a response with the saved job circular
        Response<JobCircularEntity> response = new Response<>();
        response.setData(savedJobCircular);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response<?>> getAllCircular() {

        List<JobCircularEntity> circulars = jobCircularRepository.findAll();

//        if (circulars.isEmpty()) {
//            throw ResponseEntity.ok("There is no book available in the stock");
//        }


        List<JobCircularEntity> modelList = new ArrayList<>();
        circulars.forEach(jobCircularEntity -> {
            modelList.add(
                   JobCircularEntity.builder()
                           //.admin(jobCircularEntity.getAdmin())
                           .circularId(jobCircularEntity.getCircularId())
                           .circularTitle(jobCircularEntity.getCircularTitle())
                           .jobType(jobCircularEntity.getJobType())
                           .openDate(jobCircularEntity.getOpenDate())
                           .closeDate(jobCircularEntity.getCloseDate())
                           .jobDescription(jobCircularEntity.getJobDescription())
                           .build()
            );
        });

        Response<List<JobCircularEntity>> response = new Response<List<JobCircularEntity>>(modelList, null);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Object> createEvaluator(EvaluatorCreateRequest evaluatorCreateRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long loggedInApplicantId = ((UserEntity) authentication.getPrincipal()).getUserId(); // 23

        UserEntity userAdmin = userRepository.findById(loggedInApplicantId)
                .orElseThrow(()->new IllegalArgumentException("User not found"));

        //Applicant --> user(23) -- > email

        AdminEntity admin= adminRepository.findByUser(userAdmin);

        //System.out.println(loggedInApplicantId);

        // Check if the applicantId from the ApplyRequest matches the logged-in user's applicantId
        if (!evaluatorCreateRequest.getAdminId().equals(admin.getAdminId())) {
            throw new IllegalArgumentException("Invalid admin ID");
            // or return an error response indicating that the applicant ID does not match the logged-in user
        }


        // Get the admin by adminId from the circularCreateRequest
        AdminEntity adminEntity = adminRepository.findById(evaluatorCreateRequest.getAdminId())
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));


        // Create a new UserEntity
        UserEntity user = UserEntity.builder()
                .email(evaluatorCreateRequest.getEmail())
                .password(passwordEncoder.encode(evaluatorCreateRequest.getPassword()))
                .role(Role.EVALUATOR)
                .build();


        // Save the user to the UserRepository
        UserEntity savedUser = userRepository.save(user);


        // Create a new JobCircularEntity
        EvaluatorEntity evaluatorEntity = EvaluatorEntity
                .builder()
                .admin(adminEntity)
                .user(savedUser)
                .evaluatorName(evaluatorCreateRequest.getEvaluatorName())
                .designation(evaluatorCreateRequest.getDesignation())
                .contactNumber(evaluatorCreateRequest.getContactNumber())
                .qualification(evaluatorCreateRequest.getQualification())
                .specialization(evaluatorCreateRequest.getSpecialization())
                .active(evaluatorCreateRequest.isActive())
                .build();

        // Save the job circular to the repository
        EvaluatorEntity savedEvaluator = evaluatorRepository.save(evaluatorEntity);

        // Create a response with the saved job circular
        Response<EvaluatorEntity> response = new Response<>();
        response.setData(savedEvaluator);

        //return new ResponseEntity<>(response, HttpStatus.OK);


        AuthenticationResponse authRes = AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();

        System.out.println(authRes);
        return new ResponseEntity<>(authRes, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Response<?>> getAllEvaluator() {
        List<EvaluatorEntity> evaluators = evaluatorRepository.findAll();

//        if (evaluators.isEmpty()) {
//            throw ResponseEntity.ok("There is no book available in the stock");
//        }


        List<EvaluatorEntity> modelList = new ArrayList<>();
        evaluators.forEach(evaluatorEntity -> {
            modelList.add(
                    evaluatorEntity.builder()
                            .admin(evaluatorEntity.getAdmin())
                            .user(evaluatorEntity.getUser())
                            .evaluatorId(evaluatorEntity.getEvaluatorId())
                            .evaluatorName(evaluatorEntity.getEvaluatorName())
                            .designation(evaluatorEntity.getDesignation())
                            .contactNumber(evaluatorEntity.getContactNumber())
                            .qualification(evaluatorEntity.getQualification())
                            .specialization(evaluatorEntity.getSpecialization())
                            .active(evaluatorEntity.isActive())
                            .build()
            );
        });

        Response<List<EvaluatorEntity>> response = new Response<List<EvaluatorEntity>>(modelList, null);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response<?>> createExamCategory(ExamCreateRequest examCreateRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long loggedInApplicantId = ((UserEntity) authentication.getPrincipal()).getUserId(); // 23

        UserEntity userAdmin = userRepository.findById(loggedInApplicantId)
                .orElseThrow(()->new IllegalArgumentException("User not found"));

        //Applicant --> user(23) -- > email

        AdminEntity admin= adminRepository.findByUser(userAdmin);

        //System.out.println(loggedInApplicantId);

        // Check if the applicantId from the ApplyRequest matches the logged-in user's applicantId
        if (!examCreateRequest.getAdminId().equals(admin.getAdminId())) {
            throw new IllegalArgumentException("Invalid admin ID");
            // or return an error response indicating that the applicant ID does not match the logged-in user
        }



        // Get the admin by adminId from the circularCreateRequest
        AdminEntity adminEntity = adminRepository.findById(examCreateRequest.getAdminId())
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));

        // Create a new JobCircularEntity
        ExamCategoryEntity examCategoryEntity = ExamCategoryEntity.builder()
                .admin(adminEntity)
                .examTitle(examCreateRequest.getExamTitle())
                .description(examCreateRequest.getDescription())
                .passingMarks(examCreateRequest.getPassingMarks())
                .build();


        // Save the job circular to the repository
        ExamCategoryEntity savedExamCategory = examCreateRepository.save(examCategoryEntity);

        // Create a response with the saved job circular
        Response<ExamCategoryEntity> response = new Response<>();
        response.setData(savedExamCategory);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
