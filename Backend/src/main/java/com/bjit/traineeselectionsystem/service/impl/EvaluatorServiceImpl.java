package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.AdminEntity;
import com.bjit.traineeselectionsystem.entity.EvaluatorEntity;
import com.bjit.traineeselectionsystem.entity.UserEntity;
import com.bjit.traineeselectionsystem.exception.AdminServiceException;
import com.bjit.traineeselectionsystem.exception.EvaluatorServiceException;
import com.bjit.traineeselectionsystem.exception.UserServiceException;
import com.bjit.traineeselectionsystem.model.EvaluatorModel;
import com.bjit.traineeselectionsystem.model.EvaluatorUpdateRequest;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.service.EvaluatorService;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EvaluatorServiceImpl implements EvaluatorService {

    private final RepositoryManager repositoryManager;
    @Override
    public ResponseEntity<?> getEvaluatorById(Long userId) {
        try {

            UserEntity user = repositoryManager.getUserRepository().findById(userId)
                    .orElseThrow(() -> new UserServiceException("User not found"));


            EvaluatorEntity evaluatorEntity  = repositoryManager.getEvaluatorRepository().findByUser(user)
                    .orElseThrow(() -> new EvaluatorServiceException("Evaluator not found"));


            Optional<EvaluatorEntity> optionalEvaluator = repositoryManager.getEvaluatorRepository().findById(evaluatorEntity.getEvaluatorId());

            if (optionalEvaluator.isPresent()) {

                EvaluatorModel evaluatorModel = EvaluatorModel.builder()
                        .email(optionalEvaluator.get().getUser().getEmail())
                        .evaluatorName(optionalEvaluator.get().getEvaluatorName())
                        .active(optionalEvaluator.get().isActive())
                        .contactNumber(optionalEvaluator.get().getContactNumber())
                        .designation(optionalEvaluator.get().getDesignation())
                        .qualification(optionalEvaluator.get().getQualification())
                        .specialization(optionalEvaluator.get().getSpecialization())

                        .build();

                Response<EvaluatorModel> apiResponse = new Response<>(evaluatorModel, null);

                // Return the ResponseEntity with the APIResponse
                return ResponseEntity.ok(apiResponse);

            } else {
                throw new EvaluatorServiceException("evaluator not found");
            }
        } catch (EvaluatorServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(null, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<String> updateEvaluator(EvaluatorUpdateRequest evaluatorUpdateRequest) {
        try {
            UserEntity user = repositoryManager.getUserRepository()
                    .findById(evaluatorUpdateRequest.getUserId())
                    .orElseThrow(()-> new EvaluatorServiceException("Evaluator not found"));

            Optional<EvaluatorEntity> optionalEvaluator= repositoryManager.getEvaluatorRepository()
                    .findByUser(user);


            Long adminId = 5l;


            AdminEntity adminEntity = repositoryManager.getAdminRepository().findById(adminId)
                    .orElseThrow(()->new AdminServiceException("Admin n0t found"));

            if (optionalEvaluator.isPresent()) {
                EvaluatorEntity evaluatorEntity  = optionalEvaluator.get();
                // Update the book entity with the new values from the request model
                evaluatorEntity.setAdmin(adminEntity);
                evaluatorEntity.setEvaluatorName(optionalEvaluator.get().getEvaluatorName());
                evaluatorEntity.setDesignation(optionalEvaluator.get().getDesignation());
                evaluatorEntity.setContactNumber(optionalEvaluator.get().getContactNumber());
                evaluatorEntity.setQualification(optionalEvaluator.get().getQualification());
                evaluatorEntity.setSpecialization(optionalEvaluator.get().getSpecialization());
                evaluatorEntity.setActive(true);


                // Save the updated book entity
                repositoryManager.getEvaluatorRepository()
                        .save(evaluatorEntity);

                return ResponseEntity.ok("evaluator updated successfully");

            } else {
                throw new EvaluatorServiceException("Evaluator not found");
            }
        }catch (AdminServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        catch (UserServiceException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (EvaluatorServiceException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
