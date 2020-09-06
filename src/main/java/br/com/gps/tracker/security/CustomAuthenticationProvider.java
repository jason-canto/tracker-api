package br.com.gps.tracker.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpStatusCodeException;

import br.com.gps.tracker.dto.AuthUserDto;
import br.com.gps.tracker.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final String USER_ROLE = "USER";

	@Autowired
	private AuthenticationService service;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		Assert.notNull(authentication, "authentication is mandatory.");

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		ResponseEntity<AuthUserDto> authResponse = null;
		try {
			log.info("Authenticating: {}", username);
			authResponse = service.authenticate(username, password);
			if (authResponse.getStatusCode() != null && authResponse.getStatusCode().is2xxSuccessful()) {
				List<GrantedAuthority> credentials = new ArrayList<>();
				credentials.add(new SimpleGrantedAuthority(USER_ROLE));
				log.info("Authentication successful: {}", username);
				return new UsernamePasswordAuthenticationToken(username, password, credentials);
			}
			log.error("Failed user authentication: {} {}", username, authResponse.getStatusCodeValue());
			throw new BadCredentialsException("Failed user authentication: " + authResponse.getStatusCodeValue());
		} catch (HttpStatusCodeException ex) {
			log.error("Failed user authentication: {}", ex.getMessage());
			throw new BadCredentialsException("Failed user authentication: " + ex.getMessage());
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return aClass.equals(UsernamePasswordAuthenticationToken.class);
	}
}
