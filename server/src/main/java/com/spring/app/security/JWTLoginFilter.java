package com.spring.app.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.spring.app.model.UserInfo;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException,
			IOException, ServletException {
		
		final String authorization = req.getHeader("Authorization");
		String username = "";
		String password = "";
	    if (authorization != null && authorization.startsWith("Basic")) {
	        // Authorization: Basic base64credentials
	        String base64Credentials = authorization.substring("Basic".length()).trim();
	        final byte[] decodedBytes = Base64.decode(base64Credentials.getBytes());
	        final String pair = new String(decodedBytes);
	        final String[] userDetails = pair.split(":", 2);
	        username = userDetails[0];
	        password = userDetails[1];
	    }
		
		UserInfo creds = new UserInfo();
		creds.setUsername(username);
		creds.setPassword(password);
		List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(creds.getUsername(),
						creds.getPassword(), auth));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain, Authentication auth)
			throws IOException, ServletException {
		TokenAuthenticationService.addAuthentication(res, auth.getName());
	}
}