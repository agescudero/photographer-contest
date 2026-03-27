package com.aidevelopment.photographer_contest;

import org.springframework.boot.SpringApplication;

public class TestPhotographerContestApplication {

	public static void main(String[] args) {
		SpringApplication.from(PhotographerContestApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
