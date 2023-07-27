package com.bjit.traineeselectionsystem.service.impl;

import com.bjit.traineeselectionsystem.model.AuthenticationRequest;
import com.bjit.traineeselectionsystem.model.AuthenticationResponse;
import com.bjit.traineeselectionsystem.utils.JwtService;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RepositoryManager repositoryManager;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = repositoryManager.getUserRepository().findByEmail(authenticationRequest.getEmail());
        //var user = evaluatorRepository.findByEvaluatorEmail(authenticationRequest.getEmail());

        System.out.println(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

}
