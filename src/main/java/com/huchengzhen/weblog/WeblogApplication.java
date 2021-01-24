package com.huchengzhen.weblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.service.BasicAuth;

@SpringBootApplication
@EnableWebSecurity
public class WeblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeblogApplication.class, args);
	}

}
