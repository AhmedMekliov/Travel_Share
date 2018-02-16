package com.spring.app.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.spring")
@EnableWebMvc
@Import({ SecurityConfiguration.class })
public class AppConfig {
}
