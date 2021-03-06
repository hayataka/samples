package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
@MapperScan("com.example.mapper")
public class SpringBootMybatisH2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisH2Application.class, args);
	}
}
