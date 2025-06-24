package com.hahn.stock;

import com.hahn.stock.springSecurity.jwt.JwtConfig;
import com.hahn.stock.springSecurity.jwt.JwtSecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hahn"})
@Slf4j
@EntityScan(basePackages = {"com.hahn.stock.entity"})
@EnableJpaRepositories(basePackages = {"com.hahn.stock.repository"})
@CrossOrigin(origins = "http://localhost:3000")
@Import({JwtConfig.class, JwtSecretKey.class})
public class StockBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockBackendApplication.class, args);
	}

}
