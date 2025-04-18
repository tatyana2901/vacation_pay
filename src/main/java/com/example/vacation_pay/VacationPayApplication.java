package com.example.vacation_pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching //подключение кэширования
public class VacationPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacationPayApplication.class, args);
	}

}
