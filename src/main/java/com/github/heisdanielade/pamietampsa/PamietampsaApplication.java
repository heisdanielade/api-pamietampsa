package com.github.heisdanielade.pamietampsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class PamietampsaApplication {

	public static void main(String[] args) {

		SpringApplication.run(PamietampsaApplication.class, args);
	}

}
