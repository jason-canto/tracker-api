package br.com.gps.tracker.test.filter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import br.com.gps.tracker.dto.RequestDto;
import br.com.gps.tracker.filter.PositionFilter;
import br.com.gps.tracker.publisher.MessageSenderService;

@RunWith(MockitoJUnitRunner.class)
public class PositionFilterTest {

	@Mock
	private MessageSenderService messageService;

	@InjectMocks
	private PositionFilter positionFilter = new PositionFilter();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void positionFilterTestCall() throws IOException, ServletException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		mockRequest.setRequestURI("/test");

		positionFilter.doFilter(mockRequest, mockResponse, filterChain);
		Assert.assertNotNull(mockRequest);
		Assert.assertNotNull(mockResponse);
		Assert.assertNotNull(mockRequest.getRequestURL());
		Assert.assertTrue(mockRequest.getRequestURL().toString().contains("/test"));
		Assert.assertNotNull(mockResponse.getContentAsByteArray());
		Assert.assertEquals(mockResponse.getStatus(), HttpStatus.OK.value());
		verify(messageService, times(1)).sendMessage(Mockito.any(RequestDto.class));
	}

	@Test
	public void positionFilterTestCallWithParameters() throws IOException, ServletException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		mockRequest.setRequestURI("/test?testParameter=test");

		positionFilter.doFilter(mockRequest, mockResponse, filterChain);
		Assert.assertNotNull(mockRequest);
		Assert.assertNotNull(mockResponse);
		Assert.assertNotNull(mockRequest.getRequestURL());
		Assert.assertTrue(mockRequest.getRequestURL().toString().contains("/test?testParameter=test"));
		Assert.assertNotNull(mockResponse.getContentAsByteArray());
		Assert.assertEquals(mockResponse.getStatus(), HttpStatus.OK.value());
		verify(messageService, times(1)).sendMessage(Mockito.any(RequestDto.class));
	}

	@Test
	public void positionFilterTestCallWithEmptyParameters() throws IOException, ServletException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		mockRequest.setRequestURI("/test? ");

		positionFilter.doFilter(mockRequest, mockResponse, filterChain);
		Assert.assertNotNull(mockRequest);
		Assert.assertNotNull(mockResponse);
		Assert.assertNotNull(mockRequest.getRequestURL());
		Assert.assertTrue(mockRequest.getRequestURL().toString().contains("/test"));
		Assert.assertNotNull(mockResponse.getContentAsByteArray());
		Assert.assertEquals(mockResponse.getStatus(), HttpStatus.OK.value());
		verify(messageService, times(1)).sendMessage(Mockito.any(RequestDto.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void positionFilterTestCallExpectsException() throws IOException, ServletException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		mockRequest = null;
		positionFilter.doFilter(mockRequest, mockResponse, filterChain);
		verify(messageService, times(0)).sendMessage(Mockito.any(RequestDto.class));
	}

	@Test
	public void positionFilterTestStatus() throws IOException, ServletException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		positionFilter.doFilter(mockRequest, mockResponse, filterChain);
		Assert.assertEquals(HttpStatus.OK.value(), mockResponse.getStatus());
	}

	@Test
	public void positionFilterTestRequestAfterFilter() throws IOException, ServletException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		mockRequest.setRequestURI("/test?testParameter=test");
		positionFilter.doFilter(mockRequest, mockResponse, filterChain);
		Assert.assertEquals("/test?testParameter=test", mockRequest.getRequestURI());
	}

	@Test
	public void positionFilterTestResponseAfterFilter() throws IOException, ServletException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		positionFilter.doFilter(mockRequest, mockResponse, filterChain);
		Assert.assertFalse(mockResponse.isCommitted());

		mockResponse.getOutputStream().write('X');
		Assert.assertFalse(mockResponse.isCommitted());

		int size = mockResponse.getBufferSize();
		mockResponse.getOutputStream().write(new byte[size]);
		Assert.assertTrue(mockResponse.isCommitted());
		Assert.assertEquals(size + 1, mockResponse.getContentAsByteArray().length);
	}

	@Test
	public void testResponseCommitted() throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		Assert.assertFalse(response.isCommitted());
		response.getOutputStream().write('X');
		Assert.assertFalse(response.isCommitted());
		response.flushBuffer();
		Assert.assertTrue(response.isCommitted());
		Assert.assertEquals(1, response.getContentAsByteArray().length);
	}

	@Test
	public void testServletWriterAutoFlushedForString() throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		response.getWriter().write("X");
		Assert.assertEquals("X", response.getContentAsString());
	}
}
