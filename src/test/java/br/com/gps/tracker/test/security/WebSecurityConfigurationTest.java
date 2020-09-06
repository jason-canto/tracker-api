package br.com.gps.tracker.test.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.gps.tracker.resource.PositionController;
import br.com.gps.tracker.security.CustomAuthenticationProvider;
import br.com.gps.tracker.security.CustomWebSecurityConfiguration;
import br.com.gps.tracker.service.PositionService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={CustomWebSecurityConfiguration.class, PositionController.class})
@TestPropertySource(locations = "classpath:application-integration-tests.properties")
public class WebSecurityConfigurationTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@MockBean
	private CustomAuthenticationProvider authProvider;

	@MockBean
	private PositionService service;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity(springSecurityFilterChain))
				.build();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUnauthorizedUser() throws Exception {
		when(authProvider.authenticate(any(Authentication.class))).thenReturn(null);
		when(service.findAll(anyInt(), anyInt())).thenReturn(Page.empty());
		mockMvc.perform(get("/api/tracker/positions"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	public void testAuthorizedUser() throws Exception {
		Authentication auth = new UsernamePasswordAuthenticationToken("test", "test",
				AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
		SecurityContextHolder.getContext().setAuthentication(auth);
		when(authProvider.authenticate(any(Authentication.class))).thenReturn(auth);
		when(service.findAll(anyInt(), anyInt())).thenReturn(Page.empty());

		mockMvc.perform(get("/api/tracker/positions")
				.with(httpBasic("test", "test"))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void testUnauthorizedUserCredentials() throws Exception {
		Authentication auth = new UsernamePasswordAuthenticationToken("test", "test",
				AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
		SecurityContextHolder.getContext().setAuthentication(auth);
		when(authProvider.authenticate(any(Authentication.class))).thenReturn(auth);
		when(service.findAll(anyInt(), anyInt())).thenReturn(Page.empty());

		mockMvc.perform(get("/api/tracker/positions")
				.with(httpBasic("wrongUser", "wrongPassword"))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().is4xxClientError());
	}
}
