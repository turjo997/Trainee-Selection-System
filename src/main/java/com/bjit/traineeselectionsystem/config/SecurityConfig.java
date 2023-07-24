package com.bjit.traineeselectionsystem.config;

import com.bjit.traineeselectionsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    RequestMatcher[] publicMatchers = new RequestMatcher[]{
            new AntPathRequestMatcher("/download/{applicantId}"),
            new AntPathRequestMatcher("/applicant/register"),
            new AntPathRequestMatcher("/user/login"),
            //new AntPathRequestMatcher("/evaluator/login"),
            //new AntPathRequestMatcher("/admin/login"),
            new AntPathRequestMatcher("/admin/getAllApplicant"),
            new AntPathRequestMatcher("/admin/getAllCircular"),
            new AntPathRequestMatcher("/admin/getAllEvaluator"),
            new AntPathRequestMatcher("/admin/getAllExamCategory"),
            new AntPathRequestMatcher("/admin/create/examCategory"),
            new AntPathRequestMatcher("/admin/create/evaluator"),
            new AntPathRequestMatcher("/admin/create/circular"),
            new AntPathRequestMatcher("/admin/getApplicants/{circularId}"),
            new AntPathRequestMatcher("/get/{circularId}"),
            new AntPathRequestMatcher("/applicant/apply"),
            new AntPathRequestMatcher("/applicant/get/{userId}"),
            new AntPathRequestMatcher("/admin/send/notice"),
            new AntPathRequestMatcher("/applicant/notifications/{userId}"),
            new AntPathRequestMatcher("/applicant/upload/{userId}"),
            new AntPathRequestMatcher("/applicant/get/{circularId}/{userId}"),
            new AntPathRequestMatcher("/evaluator/uploadMarks"),
            new AntPathRequestMatcher("/admin/approve/{adminId}/{circularId}/{examId}"),
            new AntPathRequestMatcher("/admin/track"),
            new AntPathRequestMatcher("/admin/approve/get/{applicantId}/{circularId}"),
            new AntPathRequestMatcher("/admin/approve"),
            new AntPathRequestMatcher("/evaluator/get/{applicantId}/{circularId}")

    };

    RequestMatcher[] adminMatchers = new RequestMatcher[]{
                    new AntPathRequestMatcher("/admin/sendMail"),
                   // new AntPathRequestMatcher("/admin/approve/written/{adminId}/{applicantId}/{circularId}/{examId}"),
                  //  new AntPathRequestMatcher("/admin/approve/technical/{adminId}/{circularId}/{examId}"),
                    new AntPathRequestMatcher("/admin/upload-marks"),
                    //new AntPathRequestMatcher("/admin/create/circular"),
                   // new AntPathRequestMatcher("/admin/create/evaluator"),
                    //new AntPathRequestMatcher("/admin/getAllEvaluator"),
                   // new AntPathRequestMatcher("/admin/create/examCategory"),
                    //new AntPathRequestMatcher("/admin/getAllCircular"),
                    new AntPathRequestMatcher("/admin/sendMail"),
                   // new AntPathRequestMatcher("/admin/approve/written/{adminId}/{applicantId}/{circularId}/{examId}"),
                    //new AntPathRequestMatcher("/admin/approve/{adminId}/{circularId}/{examId}"),
                    new AntPathRequestMatcher("/admin/upload-marks"),
                    //new AntPathRequestMatcher("/admin/getAllExamCategory"),
                   // new AntPathRequestMatcher("/admin/getAllApplicant"),
                    new AntPathRequestMatcher("/admin/generateAdmitCard"),
                   // new AntPathRequestMatcher("/admin/track"),
                   //new AntPathRequestMatcher("/admin/approve")
    };

    RequestMatcher[] applicantMatchers = new RequestMatcher[]{
            new AntPathRequestMatcher("/applicant/update"),
            new AntPathRequestMatcher("/applicant/admitCard/{applicantId}"),
            //new AntPathRequestMatcher("/applicant/upload"),
           // new AntPathRequestMatcher("/applicant/apply"),
//            new AntPathRequestMatcher("/applicant/get/{applicantId}"),
           // new AntPathRequestMatcher("/applicant/notifications/{userId}"),
            new AntPathRequestMatcher("/applicant/get/{circularId}/{userId}")
    };

    RequestMatcher[] evaluatorMatchers = new RequestMatcher[]{
//            new AntPathRequestMatcher("/evaluator/uploadMarks")
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(publicMatchers).permitAll()
                .requestMatchers(adminMatchers).hasAuthority("ADMIN")
                .requestMatchers(applicantMatchers).hasAuthority("APPLICANT")
                .requestMatchers(evaluatorMatchers).hasAuthority("EVALUATOR")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}