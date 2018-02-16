package com.spring.app;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.spring"})
@EnableAutoConfiguration
public class App extends SpringBootServletInitializer {
	
	 @Value("${spring.datasource.driverClassName}")
	 private String databaseDriverClassName;
	 
	 @Value("${spring.datasource.url}")
	 private String datasourceUrl;
	 
	 @Value("${spring.datasource.username}")
	 private String databaseUsername;
	 
	 @Value("${spring.datasource.password}")
	 private String databasePassword;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}
	
	@Bean
	public DataSource dataSource(){
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDriverClassName(databaseDriverClassName);
		dataSource.setUrl(datasourceUrl);
		dataSource.setUsername(databaseUsername);
		dataSource.setPassword(databasePassword);
		
		return dataSource;
		
	}

}
