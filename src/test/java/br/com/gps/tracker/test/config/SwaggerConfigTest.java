package br.com.gps.tracker.test.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gps.tracker.config.SwaggerConfig;
import br.com.gps.tracker.subscriber.EventListener;
import springfox.documentation.spring.web.plugins.Docket;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockBean({EventListener.class})
@TestPropertySource(locations = "classpath:application-integration-tests.properties")
public class SwaggerConfigTest {

	@LocalServerPort
	private int port;

	@Test
	public void testDocketApiConfig() {
		SwaggerConfig config = new SwaggerConfig();
		Docket configApi = config.api();
		Assert.assertNotNull(configApi);
		Assert.assertTrue(configApi.isEnabled());
	}
}
