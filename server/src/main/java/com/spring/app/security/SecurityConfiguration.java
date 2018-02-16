package com.spring.app.security;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.app.services.UserServiceImpl;

@Configuration
@EnableWebSecurity
@ComponentScan("com.spring")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/")
		.permitAll()
		.antMatchers(HttpMethod.POST, "/register")
		.permitAll()
		.anyRequest().authenticated()
		.antMatchers(HttpMethod.POST, "/login")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		// We filter the api/login requests
		.addFilterBefore(
				new JWTLoginFilter("/login", authenticationManager()),
				UsernamePasswordAuthenticationFilter.class)
		// And filter other requests to check the presence of JWT in
		// header
		.addFilterBefore(new JWTAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/", "/index.html", "/app/**", "/register", "/register/**", "/authenticate", "/favicon.ico");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);

		auth.userDetailsService(userServiceImpl).passwordEncoder(encoder);
	}

}
