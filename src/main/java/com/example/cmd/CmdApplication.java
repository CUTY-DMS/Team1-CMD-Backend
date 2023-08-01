package com.example.cmd;

import com.example.cmd.global.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class CmdApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmdApplication.class, args);
	}

}
