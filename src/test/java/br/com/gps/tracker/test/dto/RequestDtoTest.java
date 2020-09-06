package br.com.gps.tracker.test.dto;

import java.time.Instant;
import java.util.Date;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gps.tracker.dto.RequestDto;
import br.com.gps.tracker.dto.RequestStatus;

public class RequestDtoTest {

	@Test
	public void positionDtoNotNullTest() {
		RequestDto requestDto = requestDtoBuilder();
		Assert.assertNotNull(requestDto.getCustomerId());
		Assert.assertNotNull(requestDto.getProjectId());
		Assert.assertNotNull(requestDto.getRequest());
		Assert.assertNotNull(requestDto.getResponse());
		Assert.assertNotNull(requestDto.getStatus());
		Assert.assertNotNull(requestDto.getRequestDate());
		Assert.assertNotNull(requestDto.getResponseDate());
	}

	@Test
	public void equalsTest() {
		RequestDto requestDto = requestDtoBuilder();
		RequestDto copy = new RequestDto();
		copy.setCustomerId(requestDto.getCustomerId());
		copy.setProjectId(requestDto.getProjectId());
		copy.setRequest(requestDto.getRequest());
		copy.setResponse(requestDto.getResponse());
		copy.setStatus(requestDto.getStatus());
		copy.setRequestDate(requestDto.getRequestDate());
		copy.setResponseDate(requestDto.getResponseDate());

		Assert.assertEquals(copy.getCustomerId(), requestDto.getCustomerId());
		Assert.assertEquals(copy.getProjectId(), requestDto.getProjectId());
		Assert.assertEquals(copy.getRequest(), requestDto.getRequest());
		Assert.assertEquals(copy.getResponse(), requestDto.getResponse());
		Assert.assertEquals(copy.getStatus(), requestDto.getStatus());
		Assert.assertEquals(copy.getRequestDate(), requestDto.getRequestDate());
		Assert.assertEquals(copy.getResponseDate(), requestDto.getResponseDate());

		Assert.assertTrue(requestDto.equals(copy));
		Assert.assertTrue(requestDto.equals(requestDto));
	}

	@Test
	public void notEqualsTest() {
		RequestDto requestDto = requestDtoBuilder();
		RequestDto otherRequest = new RequestDto();
		otherRequest.setCustomerId("XXX");
		otherRequest.setProjectId("XXX");
		otherRequest.setRequest("{}");
		otherRequest.setResponse("{}");
		otherRequest.setStatus(RequestStatus.BROKEN);
		otherRequest.setRequestDate(new Date());
		otherRequest.setResponseDate(new Date());

		Assert.assertNotEquals(requestDto, otherRequest);
		Assert.assertNotEquals(null, otherRequest);
		Assert.assertFalse(requestDto.equals(null));
		Assert.assertFalse(requestDto.equals(new Object()));
	}

	@Test
	public void hashCodeTest() {
		RequestDto requestDto = requestDtoBuilder();
		Assert.assertNotNull(requestDto.hashCode());
	}

	@Test
	public void hashCodeNullValuesTest() {
		RequestDto requestDto = new RequestDto();
		Assert.assertNotNull(requestDto.hashCode());
	}

	@Test
	public void toStringTest() {
		RequestDto requestDto = requestDtoBuilder();
		Assert.assertNotNull(requestDto.toString());
		Assert.assertTrue(requestDto.toString().startsWith(RequestDto.class.getSimpleName()));
		Assert.assertTrue(requestDto.toString()
				.endsWith("(projectId=XYZ, customerId=XYZ, request={}, response={}, "
						+ "requestDate=Thu Jul 09 19:44:18 BRT 2020, "
						+ "responseDate=Thu Jul 09 19:44:18 BRT 2020, status=RECEIVED)"));
	}

	@Test
	public void serializationTest() {
		ObjectMapper mapper = new ObjectMapper();
		RequestDto original = new RequestDto();
		RequestDto copy = SerializationUtils.clone(original);
		Assert.assertEquals(original, copy);
		Assert.assertTrue(mapper.canSerialize(RequestDto.class));
		Assert.assertTrue(mapper.canDeserialize(mapper.constructType(RequestDto.class)));
	}

	private RequestDto requestDtoBuilder() {
		RequestDto requestDto = new RequestDto();
		requestDto.setCustomerId("XYZ");
		requestDto.setProjectId("XYZ");
		requestDto.setRequest("{}");
		requestDto.setResponse("{}");
		requestDto.setStatus(RequestStatus.RECEIVED);
		requestDto.setRequestDate(Date.from(Instant.parse("2020-07-09T22:44:18.391Z")));
		requestDto.setResponseDate(Date.from(Instant.parse("2020-07-09T22:44:18.391Z")));
		return requestDto;
	}

	@Test
	public void notEqualsByCustomerIdTest() {
		RequestDto requestDto = requestDtoBuilder();
		RequestDto copy = new RequestDto();
		copy.setCustomerId("ABC");
		copy.setProjectId(requestDto.getProjectId());
		copy.setRequest(requestDto.getRequest());
		copy.setResponse(requestDto.getResponse());
		copy.setStatus(requestDto.getStatus());
		copy.setRequestDate(requestDto.getRequestDate());
		copy.setResponseDate(requestDto.getResponseDate());

		Assert.assertFalse(requestDto.equals(copy));
		copy.setCustomerId(null);
		Assert.assertFalse(copy.equals(requestDto));
	}

	@Test
	public void notEqualsByProjectIdTest() {
		RequestDto requestDto = requestDtoBuilder();
		RequestDto copy = new RequestDto();
		copy.setCustomerId(requestDto.getCustomerId());
		copy.setProjectId("ABC");
		copy.setRequest(requestDto.getRequest());
		copy.setResponse(requestDto.getResponse());
		copy.setStatus(requestDto.getStatus());
		copy.setRequestDate(requestDto.getRequestDate());
		copy.setResponseDate(requestDto.getResponseDate());

		Assert.assertFalse(requestDto.equals(copy));
		copy.setProjectId(null);
		Assert.assertFalse(copy.equals(requestDto));
	}

	@Test
	public void notEqualsByRequestTest() {
		RequestDto requestDto = requestDtoBuilder();
		RequestDto copy = new RequestDto();
		copy.setCustomerId(requestDto.getCustomerId());
		copy.setProjectId(requestDto.getProjectId());
		copy.setRequest("{123}");
		copy.setResponse(requestDto.getResponse());
		copy.setStatus(requestDto.getStatus());
		copy.setRequestDate(requestDto.getRequestDate());
		copy.setResponseDate(requestDto.getResponseDate());

		Assert.assertFalse(requestDto.equals(copy));
		copy.setRequest(null);
		Assert.assertFalse(copy.equals(requestDto));
	}

	@Test
	public void notEqualsByResponseTest() {
		RequestDto requestDto = requestDtoBuilder();
		RequestDto copy = new RequestDto();
		copy.setCustomerId(requestDto.getCustomerId());
		copy.setProjectId(requestDto.getProjectId());
		copy.setRequest(requestDto.getRequest());
		copy.setResponse("{123}");
		copy.setStatus(requestDto.getStatus());
		copy.setRequestDate(requestDto.getRequestDate());
		copy.setResponseDate(requestDto.getResponseDate());

		Assert.assertFalse(requestDto.equals(copy));
		copy.setResponse(null);
		Assert.assertFalse(copy.equals(requestDto));
	}

	@Test
	public void notEqualsByStatusTest() {
		RequestDto requestDto = requestDtoBuilder();
		RequestDto copy = new RequestDto();
		copy.setCustomerId(requestDto.getCustomerId());
		copy.setProjectId(requestDto.getProjectId());
		copy.setRequest(requestDto.getRequest());
		copy.setResponse(requestDto.getResponse());
		copy.setStatus(RequestStatus.FAILED);
		copy.setRequestDate(requestDto.getRequestDate());
		copy.setResponseDate(requestDto.getResponseDate());

		Assert.assertFalse(requestDto.equals(copy));
	}

	@Test
	public void notEqualsByRequestDateTest() {
		RequestDto requestDto = requestDtoBuilder();
		RequestDto copy = new RequestDto();
		copy.setCustomerId(requestDto.getCustomerId());
		copy.setProjectId(requestDto.getProjectId());
		copy.setRequest(requestDto.getRequest());
		copy.setResponse(requestDto.getResponse());
		copy.setStatus(requestDto.getStatus());
		copy.setRequestDate(Date.from(Instant.parse("2007-12-03T10:15:30.00Z")));
		copy.setResponseDate(requestDto.getResponseDate());

		Assert.assertFalse(requestDto.equals(copy));
		copy.setRequestDate(null);
		Assert.assertFalse(copy.equals(requestDto));
	}

	@Test
	public void notEqualsByResponseDateIdTest() {
		RequestDto requestDto = requestDtoBuilder();
		RequestDto copy = new RequestDto();
		copy.setCustomerId(requestDto.getCustomerId());
		copy.setProjectId(requestDto.getProjectId());
		copy.setRequest(requestDto.getRequest());
		copy.setResponse(requestDto.getResponse());
		copy.setStatus(requestDto.getStatus());
		copy.setRequestDate(requestDto.getRequestDate());
		copy.setResponseDate(Date.from(Instant.parse("2007-12-03T10:15:30.00Z")));

		Assert.assertFalse(requestDto.equals(copy));
		copy.setResponseDate(null);
		Assert.assertFalse(copy.equals(requestDto));
	}

	@Test
	public void testEqualsEmptyValue() {
		RequestDto a = new RequestDto();
		RequestDto b = new RequestDto();
		Assert.assertTrue(a.equals(b) && b.equals(a));
		Assert.assertTrue(a.toString().equals(b.toString()));
		Assert.assertTrue(a.hashCode() == b.hashCode());
		Assert.assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void testEqualsNull(){
		RequestDto emptyRequest = new RequestDto();
		RequestDto nullRequest = null;
		RequestDto requestDto = requestDtoBuilder();

		Assert.assertFalse(requestDto.equals(null));
		Assert.assertFalse(requestDto.equals(nullRequest));
		Assert.assertFalse(requestDto.equals(emptyRequest));
		Assert.assertTrue(requestDto.equals(requestDto));
		Assert.assertFalse(emptyRequest.equals(null));
		Assert.assertFalse(emptyRequest.equals(nullRequest));
	}

	@Test
	public void testNoArgsConstructor() {
		RequestDto requestDto = requestDtoBuilder();
		Assert.assertNotNull(requestDto);
	}
}
