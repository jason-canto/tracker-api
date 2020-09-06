package br.com.gps.tracker.test.security;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.gps.tracker.dto.AuthUserDto;
import br.com.gps.tracker.security.CustomAuthenticationProvider;
import br.com.gps.tracker.service.AuthenticationService;

@RunWith(MockitoJUnitRunner.class)
public class CustomAuthenticationProviderTest {

	@Mock
	private AuthenticationService authServiceMock;

	@InjectMocks
	private CustomAuthenticationProvider provider = new CustomAuthenticationProvider();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void authenticateCorrectCredentialsTest() {

		List<GrantedAuthority> credentials = new ArrayList<>();
		credentials.add(new SimpleGrantedAuthority("USER"));

		Authentication authToken = new UsernamePasswordAuthenticationToken("user", "pass");
		ResponseEntity<AuthUserDto> response = new ResponseEntity<>(getAuthUserMock(), HttpStatus.OK);

		when(authServiceMock.authenticate(anyString(), anyString())).thenReturn(response);
		Authentication authenticate = provider.authenticate(authToken);

		Assert.assertTrue(authenticate.isAuthenticated());
		Assert.assertEquals(authenticate.getPrincipal(), "user");
		Assert.assertEquals(authenticate.getName(), "user");
		Assert.assertEquals(authenticate.getCredentials(), "pass");
		Assert.assertEquals(authenticate.getAuthorities(), credentials);
	}

	@Test(expected = BadCredentialsException.class) 
	public void authenticateBadCredentialsTest() {
		ResponseEntity<AuthUserDto> response = new ResponseEntity<>(getAuthUserMock(), HttpStatus.FORBIDDEN);
		Authentication authToken = new UsernamePasswordAuthenticationToken("user", "pass");
		when(authServiceMock.authenticate(anyString(), anyString())).thenReturn(response);
		provider.authenticate(authToken);
	}

	private AuthUserDto getAuthUserMock() {
		AuthUserDto authUser = new AuthUserDto();
		authUser.setLogin("user");
		authUser.setToken("XYZ");
		return authUser;
	}

}
