package br.com.gps.tracker.service;

import java.util.Base64;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import br.com.gps.tracker.dto.AuthUserDto;

@Service
public class AuthenticationService {

	private static final String HEADER_SYSTEM = "Plataform";
	private static final String HEADER_CREDENTIAL = "Cred";
	private static final String SYSTEM = "AUTHENTICATOR";
	
	private final RestTemplate restTemplate;

	@Value("${authentication.service.host}")
	private String authenticationHost;

	public AuthenticationService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder
				.build();
	}

	public ResponseEntity<AuthUserDto> authenticate(String user, String pass) throws HttpStatusCodeException {
		HttpEntity<String> request = new HttpEntity<>(createHeaders(user, pass));
		return restTemplate.exchange(authenticationHost, HttpMethod.POST, request, AuthUserDto.class);
	}

	private HttpHeaders createHeaders(String user, String pass) {
		String base64Creds = getEncodedCredentials(user, pass);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add(HEADER_SYSTEM, SYSTEM);
		headers.add(HEADER_CREDENTIAL, "Basic " + base64Creds);
		return headers;
	}

	private String getEncodedCredentials(String user, String pass) {
		String authStr = StringUtils.replace(user, "@", "%") + ":" + pass;
		return Base64.getEncoder().encodeToString(authStr.getBytes());
	}
}
