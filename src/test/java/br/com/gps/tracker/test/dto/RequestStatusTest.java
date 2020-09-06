package br.com.gps.tracker.test.dto;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import br.com.gps.tracker.dto.RequestStatus;

public class RequestStatusTest {

	@Test
	public void requestStatusTest() {
		assertThat(RequestStatus.RECEIVED.name(), containsString("RECEIVED"));
		assertThat(RequestStatus.BROKEN.name(), containsString("BROKEN"));
		assertThat(RequestStatus.FAILED.name(), containsString("FAILED"));
	}

}
