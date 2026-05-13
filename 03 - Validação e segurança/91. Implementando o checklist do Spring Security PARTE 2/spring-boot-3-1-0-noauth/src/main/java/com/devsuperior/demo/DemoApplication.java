package com.devsuperior.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication /*implements CommandLineRunner */{

	/*@Autowired
	private PasswordEncoder passwordEncoder;*/


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/*@Override
	public void run(String... args) throws Exception {
		System.out.println("BCRYPT: " + passwordEncoder.encode("123456"));
		boolean result = passwordEncoder.matches("123456", "$2a$10$7onROsxzoABat/qkAVC18.zFEA..30q2u5wAzs8kQX0H8UFeJm8O6");
		System.out.println("RESULTADO = " + result);
	}*/
}
