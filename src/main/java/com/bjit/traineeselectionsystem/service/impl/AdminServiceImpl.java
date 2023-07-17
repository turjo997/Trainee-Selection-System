package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.*;
import com.bjit.traineeselectionsystem.model.*;
import com.bjit.traineeselectionsystem.repository.*;
import com.bjit.traineeselectionsystem.service.AdminService;
import com.bjit.traineeselectionsystem.utils.JwtService;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final RepositoryManager repositoryManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public ResponseEntity<String> createCircular(CircularCreateRequest circularCreateRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long loggedInApplicantId = ((UserEntity) authentication.getPrincipal()).getUserId();

            UserEntity user = repositoryManager.getUserRepository().findById(loggedInApplicantId)
                    .orElseThrow(() -> new UserServiceException("User not found"));

            AdminEntity admin = repositoryManager.getAdminRepository().findByUser(user);

            if (!circularCreateRequest.getAdminId().equals(admin.getAdminId())) {
                throw new IllegalArgumentException("Invalid admin ID");
            }

            AdminEntity adminEntity = repositoryManager.getAdminRepository().findById(circularCreateRequest.getAdminId())
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            // Check if a job circular with the same title already exists
            boolean isCircularTitleExists = repositoryManager.getJobCircularRepository().existsByCircularTitle(circularCreateRequest.getCircularTitle());
            if (isCircularTitleExists) {
                throw new IllegalArgumentException("Job circular with the same title already exists");
            }

            // Check if the open date is valid (e.g., not in the past)
            LocalDate openDate = circularCreateRequest.getOpenDate();
            LocalDate currentDate = LocalDate.now();
            if (openDate.isBefore(currentDate)) {
                throw new IllegalArgumentException("Open date should be a valid future date");
            }

            // Check if the close date is after the open date
            LocalDate closeDate = circularCreateRequest.getCloseDate();
            if (closeDate.isBefore(openDate)) {
                throw new IllegalArgumentException("Close date should be after the open date");
            }

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


            repositoryManager.getJobCircularRepository().save(jobCircularEntity);

            return ResponseEntity.ok("Job circular created successfully");
        } catch (UserServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (AdminServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<Object> createEvaluator(EvaluatorCreateRequest evaluatorCreateRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long loggedInApplicantId = ((UserEntity) authentication.getPrincipal()).getUserId();

            UserEntity userAdmin = repositoryManager.getUserRepository().findById(loggedInApplicantId)
                    .orElseThrow(() -> new UserServiceException("User not found"));

            AdminEntity admin = repositoryManager.getAdminRepository().findByUser(userAdmin);

            if (!evaluatorCreateRequest.getAdminId().equals(admin.getAdminId())) {
                throw new IllegalArgumentException("Invalid admin ID");
            }

            AdminEntity adminEntity = repositoryManager.getAdminRepository().findById(evaluatorCreateRequest.getAdminId())
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            if (repositoryManager.getUserRepository().existsByEmail(evaluatorCreateRequest.getEmail())) {
                String errorMessage = "Evaluator with the same email already exists";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }

            UserEntity user = UserEntity.builder()
                    .email(evaluatorCreateRequest.getEmail())
                    .password(passwordEncoder.encode(evaluatorCreateRequest.getPassword()))
                    .role(Role.EVALUATOR)
                    .build();

            UserEntity savedUser = repositoryManager.getUserRepository().save(user);

            EvaluatorEntity evaluatorEntity = EvaluatorEntity.builder()
                    .admin(adminEntity)
                    .user(savedUser)
                    .evaluatorName(evaluatorCreateRequest.getEvaluatorName())
                    .designation(evaluatorCreateRequest.getDesignation())
                    .contactNumber(evaluatorCreateRequest.getContactNumber())
                    .qualification(evaluatorCreateRequest.getQualification())
                    .specialization(evaluatorCreateRequest.getSpecialization())
                    .active(evaluatorCreateRequest.isActive())
                    .build();

            repositoryManager.getEvaluatorRepository().save(evaluatorEntity);

            AuthenticationResponse authRes = AuthenticationResponse.builder()
                    .token(jwtService.generateToken(user))
                    .build();

            return new ResponseEntity<>(authRes, HttpStatus.CREATED);
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AdminServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<String> createExamCategory(ExamCreateRequest examCreateRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long loggedInApplicantId = ((UserEntity) authentication.getPrincipal()).getUserId();

            UserEntity userAdmin = repositoryManager.getUserRepository().findById(loggedInApplicantId)
                    .orElseThrow(() -> new UserServiceException("User not found"));

            AdminEntity admin = repositoryManager.getAdminRepository().findByUser(userAdmin);

            if (!examCreateRequest.getAdminId().equals(admin.getAdminId())) {
                throw new IllegalArgumentException("Invalid admin ID");
            }

            AdminEntity adminEntity = repositoryManager.getAdminRepository().findById(examCreateRequest.getAdminId())
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            ExamCategoryEntity examCategoryEntity = ExamCategoryEntity.builder()
                    .admin(adminEntity)
                    .examTitle(examCreateRequest.getExamTitle())
                    .description(examCreateRequest.getDescription())
                    .passingMarks(examCreateRequest.getPassingMarks())
                    .build();

            repositoryManager.getExamCreateRepository().save(examCategoryEntity);

            return ResponseEntity.ok("Exam category created successfully");
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AdminServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<Response<?>> getAllApplicant() {
        try {
            List<ApplicantEntity> applicants = repositoryManager.getApplicantRepository().findAll();
            if (applicants.isEmpty()) {
                throw new ApplicantServiceException("No applicant found");
            }

            List<ApplicantEntity> modelList = new ArrayList<>();
            applicants.forEach(applicant -> {
                modelList.add(
                        ApplicantEntity.builder()
                                .applicantId(applicant.getApplicantId())
                                .firstName(applicant.getFirstName())
                                .lastName(applicant.getLastName())
                                .dob(applicant.getDob())
                                .cgpa(applicant.getCgpa())
                                .address(applicant.getAddress())
                                .contact(applicant.getContact())
                                .degreeName(applicant.getDegreeName())
                                .institute(applicant.getInstitute())
                                .gender(applicant.getGender())
                                .passingYear(applicant.getPassingYear())
                                .build()
                );
            });
            Response<List<ApplicantEntity>> response = new Response<>(modelList, null);
            return ResponseEntity.ok(response);
        }
        catch (ApplicantServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<Response<?>> getAllExamCategory() {
        try {
            List<ExamCategoryEntity> categories = repositoryManager.getExamCreateRepository().findAll();
            if (categories.isEmpty()) {
                throw new ExamCreateServiceException("No exam categories found");
            }

            List<ExamCategoryEntity> modelList = new ArrayList<>();
            categories.forEach(examCategory -> {
                modelList.add(
                        ExamCategoryEntity.builder()
                                .examId(examCategory.getExamId())
                                .examTitle(examCategory.getExamTitle())
                                .description(examCategory.getDescription())
                                .passingMarks(examCategory.getPassingMarks())
                                .build()
                );
            });
            Response<List<ExamCategoryEntity>> response = new Response<>(modelList, null);
            return ResponseEntity.ok(response);
        }
        catch (ExamCreateServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<Response<?>> getAllCircular() {

        try {
            List<JobCircularEntity> circulars = repositoryManager.getJobCircularRepository().findAll();
            if (circulars.isEmpty()) {
                throw new JobCircularServiceException("There is no circular open right now");
            }

            List<JobCircularEntity> modelList = new ArrayList<>();
            circulars.forEach(jobCircularEntity -> {
                modelList.add(
                        JobCircularEntity.builder()
                                .admin(jobCircularEntity.getAdmin())
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
        catch (JobCircularServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(e.getMessage(), null));
        }

    }

    @Override
    public ResponseEntity<Response<?>> getAllEvaluator() {

        try {

            List<EvaluatorEntity> evaluators = repositoryManager.getEvaluatorRepository().findAll();

            if (evaluators.isEmpty()) {
                throw new EvaluatorServiceException("No evaluator found");
            }

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
        catch (EvaluatorServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(e.getMessage(), null));
        }

    }
}
