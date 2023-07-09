package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.entity.AdminEntity;
import com.bjit.traineeselectionsystem.entity.UserEntity;
import com.bjit.traineeselectionsystem.model.Response;
import com.bjit.traineeselectionsystem.repository.AdminRepository;
import com.bjit.traineeselectionsystem.repository.UserRepository;
import com.bjit.traineeselectionsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    @Override
    public void addAdmin() {

        String email = "admin@gmail.com";
        String password = hashPass("admin12345");
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

    private String hashPass(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(pass.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception appropriately
            return null;
        }
    }

}
