package com.bjit.traineeselectionsystem.config;


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
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    RequestMatcher[] publicMatchers = new RequestMatcher[]{
            new AntPathRequestMatcher("/applicant/register"),
            new AntPathRequestMatcher("/user/login"),
    };

    RequestMatcher[] adminMatchers = new RequestMatcher[]{
                    new AntPathRequestMatcher("/admin/getApplicants/{circularId}"),
                    new AntPathRequestMatcher("/admin/getApplicants/{circularId}/{examId}"),
                    new AntPathRequestMatcher("/admin/get/{applicantId}/{circularId}/{examId}"),
                    new AntPathRequestMatcher("/admin/generate/{circularId}"),
                    new AntPathRequestMatcher("/admin/sendMail"),
                    new AntPathRequestMatcher("/admin/uploadMarks"),
                    new AntPathRequestMatcher("/admin/create/circular"),
                    new AntPathRequestMatcher("/admin/create/evaluator"),
                    new AntPathRequestMatcher("/admin/create/examCategory"),
                    new AntPathRequestMatcher("/admin/getAllEvaluator"),
                    new AntPathRequestMatcher("/admin/getAllApplicant"),
                    new AntPathRequestMatcher("/admin/get/trainees/{circularId}"),
                    new AntPathRequestMatcher("/admin/approve/get/{applicantId}/{circularId}"),
                    new AntPathRequestMatcher("/admin/approve"),
                    new AntPathRequestMatcher("/admin/track"),
                    new AntPathRequestMatcher("/admin/send/notice"),
                    new AntPathRequestMatcher("/admin/approve/{userId}/{circularId}/{examId}")
    };

    RequestMatcher[] applicantMatchers = new RequestMatcher[]{
            new AntPathRequestMatcher("/applicant/apply"),
            new AntPathRequestMatcher("/applicant/get/{userId}"),
            new AntPathRequestMatcher("/applicant/notifications/{userId}"),
            new AntPathRequestMatcher("/applicant/upload/{userId}"),
            new AntPathRequestMatcher("/applicant/update"),
            new AntPathRequestMatcher("/download/{userId}"),
            new AntPathRequestMatcher("/applicant/get/{circularId}/{userId}"),
            new AntPathRequestMatcher("/get/{circularId}"),



//            new AntPathRequestMatcher("/applicant/get/{applicantId}"),

          //  new AntPathRequestMatcher("/applicant/get/{circularId}/{userId}")
    };

    RequestMatcher[] evaluatorMatchers = new RequestMatcher[]{
            new AntPathRequestMatcher("/evaluator/uploadMarks"),
            new AntPathRequestMatcher("/evaluator/get/{userId}"),
            new AntPathRequestMatcher("/evaluator/update"),
            new AntPathRequestMatcher("/evaluator/get/{applicantId}/{circularId}"),
    };

    RequestMatcher[] adminAndEvaluatorMatchers = new RequestMatcher[]{
            new AntPathRequestMatcher("/admin/getAllExamCategory"),
            new AntPathRequestMatcher("/admin/get/written/{circularId}"),
    };

    RequestMatcher[] adminAndEvaluatorAndApplicantMatchers = new RequestMatcher[]{
            new AntPathRequestMatcher("/admin/getAllCircular")
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(publicMatchers).permitAll()
                .requestMatchers(adminMatchers).hasAuthority("ADMIN")
                .requestMatchers(applicantMatchers).hasAuthority("APPLICANT")
                .requestMatchers(evaluatorMatchers).hasAuthority("EVALUATOR")
                .requestMatchers(adminAndEvaluatorMatchers).hasAnyAuthority( "ADMIN" , "EVALUATOR")
                .requestMatchers(adminAndEvaluatorAndApplicantMatchers).hasAnyAuthority( "APPLICANT" , "ADMIN" , "EVALUATOR")
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