package br.com.gps.tracker.test.service;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gps.tracker.dto.AuthUserDto;
import br.com.gps.tracker.service.AuthenticationService;

@RestClientTest(AuthenticationService.class)
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration
public class AuthenticationServiceIntegrationTest {

	@Autowired
	private AuthenticationService authService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockRestServiceServer mockRestServiceServer;

	private static final String TEST_AUTH_SERVICE = "http://test/authentication-service";

	@Before
	public void setup() {
		ReflectionTestUtils.setField(authService, "authenticationHost", TEST_AUTH_SERVICE);
	}

	@Test
	public void userClientSuccessfullyReturnsOk() throws Exception {

		AuthUserDto authUser = new AuthUserDto();
		authUser.setLogin("test");
		authUser.setToken("XYZ");
		String json = this.objectMapper.writeValueAsString(authUser);

		this.mockRestServiceServer.expect(requestTo(TEST_AUTH_SERVICE))
				.andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

		ResponseEntity<AuthUserDto> result = authService.authenticate("test", "XYZ");
		String resultJson = this.objectMapper.writeValueAsString(result.getBody());
		Assert.assertNotNull(result);
		Assert.assertTrue(result.hasBody());
		Assert.assertTrue(result.getStatusCode().is2xxSuccessful());
		Assert.assertTrue(json.equals(resultJson));
	}
}
