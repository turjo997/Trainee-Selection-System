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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(  "/download/{applicantId}" , "evaluator/upload-marks","/applicant/register" , "/hello/**","/applicant/login" , "/evaluator/login" , "/admin/login" , "admin/getAllCircular")
                .permitAll()
                .requestMatchers("/admin/sendMail" , "/admin/approve/written/{adminId}/{applicantId}/{circularId}/{examId}","/admin/approve/technical/{adminId}/{circularId}/{examId}","admin/upload-marks" , "/admin/create/circular" ,"/admin/create/evaluator" , "/admin/getAllEvaluator" , "admin/create/examCategory").hasAuthority("ADMIN")
                .requestMatchers("/applicant/admitCard/{applicantId}" , "/applicant/upload" , "/applicant/apply").hasAuthority("APPLICANT")
                .requestMatchers("evaluator/upload-marks").hasAnyAuthority( "EVALUATOR")
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