package com.bjit.traineeselectionsystem;

import com.bjit.traineeselectionsystem.service.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class TraineeSelectionSystemApplication {

	public static void main(String[] args) throws Exception {

		ApplicationContext applicationContext = SpringApplication.run(TraineeSelectionSystemApplication.class, args);

		UserService userService = applicationContext.getBean(UserService.class);

		userService.addAdmin();

//		CodeGeneratorService codeGeneratorService = applicationContext.getBean(CodeGeneratorService.class);
//		codeGeneratorService.writeQRCode(4L);
	}

}
