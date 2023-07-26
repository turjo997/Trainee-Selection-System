package com.bjit.traineeselectionsystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.bjit.traineeselectionsystem.entity.AdminEntity;
import com.bjit.traineeselectionsystem.entity.UserEntity;
import com.bjit.traineeselectionsystem.model.CircularCreateRequest;
import com.bjit.traineeselectionsystem.repository.AdminRepository;
import com.bjit.traineeselectionsystem.repository.JobCircularRepository;
import com.bjit.traineeselectionsystem.repository.UserRepository;
import com.bjit.traineeselectionsystem.service.impl.AdminServiceImpl;
import com.bjit.traineeselectionsystem.utils.JwtService;
import com.bjit.traineeselectionsystem.utils.RepositoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminServiceImplTest {

    private AdminServiceImpl adminService;
    private RepositoryManager repositoryManager;
    private UserRepository userRepository;
    private AdminRepository adminRepository;
    private JobCircularRepository jobCircularRepository;

    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        // Create mock instances of the required dependencies
        repositoryManager = mock(RepositoryManager.class);
        userRepository = mock(UserRepository.class);
        adminRepository = mock(AdminRepository.class);
        jobCircularRepository = mock(JobCircularRepository.class);

        // Associate the mocks with the repository manager
        when(repositoryManager.getUserRepository()).thenReturn(userRepository);
        when(repositoryManager.getAdminRepository()).thenReturn(adminRepository);
        when(repositoryManager.getJobCircularRepository()).thenReturn(jobCircularRepository);

        // Instantiate the AdminServiceImpl with the mock RepositoryManager
        adminService = new AdminServiceImpl(repositoryManager,  passwordEncoder ,  jwtService );
    }

    @Test
    public void testCreateCircular_Success() {
        // Define your input data for the test
        CircularCreateRequest request = new CircularCreateRequest();
        long userId = 12L;
        request.setUserId(userId);
        request.setCircularTitle("Sample Job Circular 44");
        request.setJobType("Full-time");
        request.setOpenDate(LocalDate.parse("2023-07-30"));
        request.setCloseDate(LocalDate.parse("2023-09-30"));
        request.setJobDescription("This is a sample job description.");
        request.setStatus("Active");

        // Mock the behavior of the userRepository and adminRepository methods
        UserEntity userEntity = new UserEntity();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        AdminEntity adminEntity = new AdminEntity();
        when(adminRepository.findByUser(userEntity)).thenReturn(Optional.of(adminEntity));

        when(jobCircularRepository.existsByCircularTitle(anyString())).thenReturn(false); // Assuming no circular with the same title exists

        // Call the method to be tested
        ResponseEntity<String> response = adminService.createCircular(request);

        // Assert the expected HttpStatus
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert the expected response body
        assertEquals("Job circular created successfully", response.getBody());
    }

    @Test
    public void testCreateCircular_UserNotFound() {
        // Define your input data for the test
        CircularCreateRequest request = new CircularCreateRequest(/* Fill with appropriate values */);

        long userId = 1L;
        request.setUserId(userId);

        // Mock the behavior of the userRepository method to return empty Optional (user not found)
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Call the method to be tested and assert the expected exception
        ResponseEntity<String> response = adminService.createCircular(request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    public void testCreateCircular_AdminNotFound() {
        // Define your input data for the test
        CircularCreateRequest request = new CircularCreateRequest(/* Fill with appropriate values */);
        long userId = 1L;
        request.setUserId(userId);

        // Mock the behavior of the userRepository method to return a user
        UserEntity userEntity = new UserEntity(/* Fill with appropriate values */);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        // Mock the behavior of the adminRepository method to return empty Optional (admin not found)
        when(adminRepository.findByUser(userEntity)).thenReturn(Optional.empty());

        // Call the method to be tested and assert the expected exception
        ResponseEntity<String> response = adminService.createCircular(request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Admin not found", response.getBody());
    }

    @Test
    public void testCreateCircular_DuplicateCircularTitle() {
        // Define your input data for the test
        CircularCreateRequest request = new CircularCreateRequest();
        long userId = 12L;
        request.setUserId(userId);
        request.setCircularTitle("Sample Job Circular");
        request.setJobType("Full-time");
        request.setOpenDate(LocalDate.parse("2023-07-24"));
        request.setCloseDate(LocalDate.parse("2023-08-24"));
        request.setJobDescription("This is a sample job description.");
        request.setStatus("Active");

        // Mock the behavior of the userRepository and adminRepository methods
        UserEntity userEntity = new UserEntity();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

        AdminEntity adminEntity = new AdminEntity();
        when(adminRepository.findByUser(any(UserEntity.class))).thenReturn(Optional.of(adminEntity));

        // Mock the behavior of the jobCircularRepository method to return true (circular title exists)
        when(jobCircularRepository.existsByCircularTitle(anyString())).thenReturn(true);

        // Call the method to be tested and assert the expected exception
        ResponseEntity<String> response = adminService.createCircular(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Job circular with the same title already exists", response.getBody());
    }
    @Test
    public void testCreateCircular_InvalidOpenDate() {
        // Define your input data for the test with an open date in the past
        CircularCreateRequest request = new CircularCreateRequest();
        long userId = 12L;
        request.setUserId(userId);
        request.setCircularTitle("Sample Job Circular");
        request.setJobType("Full-time");
        // Set an open date in the past
        request.setOpenDate(LocalDate.parse("2021-07-24"));
        request.setCloseDate(LocalDate.parse("2023-08-24"));
        request.setJobDescription("This is a sample job description.");
        request.setStatus("Active");

        // Mock the behavior of the userRepository and adminRepository methods
        UserEntity userEntity = new UserEntity();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));

        AdminEntity adminEntity = new AdminEntity();
        when(adminRepository.findByUser(any(UserEntity.class))).thenReturn(Optional.of(adminEntity));

        when(jobCircularRepository.existsByCircularTitle(anyString())).thenReturn(false); // Assuming no circular with the same title exists

        // Call the method to be tested and assert the expected exception
        ResponseEntity<String> response = adminService.createCircular(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Open date should be a valid future date", response.getBody());
    }
}
