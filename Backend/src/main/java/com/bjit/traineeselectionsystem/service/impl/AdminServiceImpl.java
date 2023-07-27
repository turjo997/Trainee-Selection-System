package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.*;
import com.bjit.traineeselectionsystem.exception.*;
import com.bjit.traineeselectionsystem.model.*;
import com.bjit.traineeselectionsystem.service.AdminService;
import com.bjit.traineeselectionsystem.utils.ApplicantRankComparator;
import com.bjit.traineeselectionsystem.utils.JwtService;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final RepositoryManager repositoryManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public ResponseEntity<String> createCircular(CircularCreateRequest circularCreateRequest) {
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            Long loggedInApplicantId = ((UserEntity) authentication.getPrincipal()).getUserId();
//
//            UserEntity user = repositoryManager.getUserRepository().findById(loggedInApplicantId)
//                    .orElseThrow(() -> new UserServiceException("User not found"));
//
//            AdminEntity admin = repositoryManager.getAdminRepository().findByUser(user)
//                    .orElseThrow(() -> new AdminServiceException("Admin not found"));
//
//            if (!circularCreateRequest.getAdminId().equals(admin.getAdminId())) {
//                throw new IllegalArgumentException("Invalid admin ID");
//            }

            UserEntity user = repositoryManager.getUserRepository().findById(circularCreateRequest.getUserId())
                    .orElseThrow(() -> new UserServiceException("User not found"));

            AdminEntity adminEntity = repositoryManager.getAdminRepository()
                    .findByUser(user)
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
    public ResponseEntity<Object> createEvaluator(EvaluatorModel evaluatorModel) {
        try {
            UserEntity userEntity = repositoryManager.getUserRepository()
                    .findById(evaluatorModel.getUserId())
                    .orElseThrow(() -> new UserServiceException("User not found"));

            AdminEntity adminEntity = repositoryManager.getAdminRepository().findByUser(userEntity)
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            if (repositoryManager.getUserRepository().existsByEmail(evaluatorModel.getEmail())) {
                String errorMessage = "Evaluator with the same email already exists";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }

            UserEntity user = UserEntity.builder()
                    .email(evaluatorModel.getEmail())
                    .password(passwordEncoder.encode(evaluatorModel.getPassword()))
                    .role(Role.EVALUATOR)
                    .build();

            UserEntity savedUser = repositoryManager.getUserRepository().save(user);

            EvaluatorEntity evaluatorEntity = EvaluatorEntity.builder()
                    .admin(adminEntity)
                    .user(savedUser)
                    .evaluatorName(evaluatorModel.getEvaluatorName())
                    .designation(evaluatorModel.getDesignation())
                    .contactNumber(evaluatorModel.getContactNumber())
                    .qualification(evaluatorModel.getQualification())
                    .specialization(evaluatorModel.getSpecialization())
                    .active(evaluatorModel.isActive())
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
                                .admin(examCategory.getAdmin())
                                .examTitle(examCategory.getExamTitle())
                                .description(examCategory.getDescription())
                                .passingMarks(examCategory.getPassingMarks())
                                .build()
                );
            });
            Response<List<ExamCategoryEntity>> response = new Response<>(modelList, null);
            return ResponseEntity.ok(response);
        } catch (ExamCreateServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(e.getMessage(), null));
        }
    }


    @Override
    public ResponseEntity<?> getCircularById(Long circularId) {
        try {
            Optional<JobCircularEntity> optionalCircular = repositoryManager.getJobCircularRepository().findById(circularId);
            if (optionalCircular.isPresent()) {

                CircularCreateRequest jobModel = CircularCreateRequest.builder()

                        .circularId(optionalCircular.get().getCircularId())
                        .circularTitle(optionalCircular.get().getCircularTitle())
                        .jobType(optionalCircular.get().getJobType())
                        .openDate(optionalCircular.get().getOpenDate())
                        .closeDate(optionalCircular.get().getCloseDate())
                        .jobDescription(optionalCircular.get().getJobDescription())
                        .build();

                Response<CircularCreateRequest> apiResponse = new Response<>(jobModel, null);

                // Return the ResponseEntity with the APIResponse
                return ResponseEntity.ok(apiResponse);

            } else {
                throw new JobCircularServiceException("circular not found");
            }
        } catch (JobCircularServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<String> sendNotice(NoticeModel noticeModel) {
        try {

            UserEntity user = repositoryManager.getUserRepository().findById(noticeModel.getUserId())
                    .orElseThrow(() -> new UserServiceException("User not found"));
            AdminEntity adminEntity = repositoryManager.getAdminRepository().findByUser(user)
                    .orElseThrow(() -> new AdminServiceException("Admin not found"));

            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(noticeModel.getCircularId())
                    .orElseThrow(() -> new JobCircularServiceException("Circular not found"));

            ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository().findById(noticeModel.getExamId())
                    .orElseThrow(() -> new ExamCreateServiceException("Exam not found"));

            // Get the approved applicants for a specific circular and exam
            List<ApproveEntity> selectedApplicants = repositoryManager.getApproveRepository()
                    .findByJobCircularCircularIdAndExamCategoryExamId(noticeModel.getCircularId(), noticeModel.getExamId());

            List<NotificationEntity> addNotice = new ArrayList<>();


            System.out.println(selectedApplicants.size());

            if (selectedApplicants.size() > 0) {

                for (ApproveEntity approve : selectedApplicants) {

                    System.out.println(approve.getApplicant().getApplicantId());
                    System.out.println(approve.getExamCategory().getExamId());
                    System.out.println(approve.getJobCircular().getCircularId());


                    NotificationEntity notificationEntity = NotificationEntity.builder()
                            .admin(adminEntity)
                            .applicant(approve.getApplicant())
                            .description(noticeModel.getDescription())
                            .title(noticeModel.getTitle())
                            .build();
                    addNotice.add(notificationEntity);
                }
                // Save the generated AdmitCardEntity to the database or perform any other necessary actions
                repositoryManager.getNoticeBoardRepository().saveAll(addNotice);

                return ResponseEntity.ok("applicant notified successfully");

            } else {
                throw new ApproveServiceException("No approval found for the given circular and exam");
            }

        }catch (UserServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (AdminServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (JobCircularServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ExamCreateServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ApproveServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Log the error or handle it as per your application's requirements
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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
        } catch (JobCircularServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
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
                        EvaluatorEntity.builder()
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
        } catch (EvaluatorServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(e.getMessage(), null));
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
                                .user(applicant.getUser())
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
        } catch (ApplicantServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(e.getMessage(), null));
        }
    }




    @Override
    public boolean isMarksUploadedByApplicantId(Long applicantId, Long circularId , Long examId) {
        try {
            if(examId == 3){
                repositoryManager.getApplicantRepository()
                        .findById(applicantId)
                        .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

                boolean flag = repositoryManager.getUploadMarksByAdminRepository()
                        .isMarksUploaded(applicantId,  circularId , examId);


                if (flag) {
                    // The user has applied for the job circular
                    return flag;
                } else {
                    // The user has not applied for the job circular
                    return flag;
                }
            }

            else {
                repositoryManager.getApplicantRepository()
                        .findById(applicantId)
                        .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

                boolean flag = repositoryManager.getUploadMarksByAdminRepository()
                        .isMarksUploaded(applicantId,  circularId , examId);


                if (flag) {
                    // The user has applied for the job circular
                    return flag;
                } else {
                    // The user has not applied for the job circular
                    return flag;
                }
            }

        }  catch (ApplicantServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage())).hasBody();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(null, e.getMessage())).hasBody();
        }
    }

    @Override
    public ResponseEntity<Response<?>> getTrainees(Long circularId) {

        try {

            Long examId = 4l;
            Long adminId = 5l;

            AdminEntity adminEntity = repositoryManager.getAdminRepository().findById(adminId)
                    .orElseThrow(()->new AdminServiceException("Admin not found"));

            JobCircularEntity jobCircularEntity = repositoryManager.getJobCircularRepository().findById(circularId)
                    .orElseThrow(() -> new JobCircularServiceException("Circular not found"));
            ExamCategoryEntity examCategoryEntity = repositoryManager.getExamCreateRepository().findById(examId)
                    .orElseThrow(() -> new ExamCreateServiceException("Exam not found"));


            List<UploadMarksByAdminEntity> uploadMarksListForHR = repositoryManager.getUploadMarksByAdminRepository()
                    .findAllByJobCircularCircularIdAndExamCategoryExamId(circularId , examId);

            // Calculate applicant ranks based on marks
            List<ApplicantRank> applicantRanksForHR = new ArrayList<>();


            if (uploadMarksListForHR.isEmpty()) {
                // The list is empty
                System.out.println("The list is  empty");
                throw new IllegalArgumentException("HR test has not been taken yet");

            } else {
                // The list is not empty

                System.out.println("The list is not empty");

                for (UploadMarksByAdminEntity uploadMarks : uploadMarksListForHR) {
                    ApplicantRank applicantRank = new ApplicantRank();
                    applicantRank.setApplicantId(uploadMarks.getApplicant().getApplicantId());
                    applicantRank.setMarks(uploadMarks.getMarks());
                    applicantRanksForHR.add(applicantRank);
                }

                // Sort applicant ranks in descending order of marks
                applicantRanksForHR.sort(new ApplicantRankComparator());

                // Select top 20 applicants
                List<ApplicantRank> topApplicantsForWritten = applicantRanksForHR
                        .subList(0, Math.min(applicantRanksForHR.size(), 20));


                System.out.println(topApplicantsForWritten);

                // Approve top applicants
                for (ApplicantRank applicantRank : topApplicantsForWritten) {


                    ApplicantEntity applicant = repositoryManager.getApplicantRepository()
                            .findById(applicantRank.getApplicantId())
                            .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

                    if (repositoryManager.getFinalTraineesRepository()
                            .existsByApplicantAndJobCircular
                                    (applicant , jobCircularEntity)) {
                        // Entry already exists, skip creating a new one
                        continue;
                    }

                    FinalTraineesList finalTraineesList = FinalTraineesList.builder()
                            .admin(adminEntity)
                            .jobCircular(jobCircularEntity)
                            .applicant(applicant)
                            .build();

                    repositoryManager.getFinalTraineesRepository().save(finalTraineesList);

                }

            }

            List<FinalTraineesList> finalTrainees = repositoryManager.getFinalTraineesRepository().
                    findAllByJobCircularCircularId(circularId);

            List<ApplicantEntity> applicantEntities = new ArrayList<>();

            // Fetch the applicants based on the applicantIds present in the finalTrainees list
            for (FinalTraineesList finalTrainee : finalTrainees) {
                Long applicantId = finalTrainee.getApplicant()
                        .getApplicantId();
                ApplicantEntity applicant = repositoryManager.getApplicantRepository()
                        .findById(applicantId)
                        .orElseThrow(() -> new ApplicantServiceException("Applicant not found"));

                applicantEntities.add(applicant);
            }

            Response<List<ApplicantEntity>> response = new Response<>(applicantEntities, null);


            return ResponseEntity.ok(response);

        }catch (JobCircularServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null , e.getMessage()));
        }
        catch (AdminServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null , e.getMessage()));
        }catch (ExamCreateServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null , e.getMessage()));
        }
        catch (ApplicantServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null , e.getMessage()));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null , e.getMessage()));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(null , e.getMessage()));
        }
    }


}
