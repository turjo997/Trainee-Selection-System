package com.bjit.traineeselectionsystem;

import com.bjit.traineeselectionsystem.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TraineeSelectionSystemApplication {


	public static void main(String[] args) {


		ApplicationContext applicationContext = SpringApplication.run(TraineeSelectionSystemApplication.class, args);

		UserService userService = applicationContext.getBean(UserService.class);

		userService.addAdmin();
	}

}
