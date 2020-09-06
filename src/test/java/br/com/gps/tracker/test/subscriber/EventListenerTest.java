package br.com.gps.tracker.test.subscriber;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gps.tracker.dto.PositionDto;
import br.com.gps.tracker.service.PositionService;
import br.com.gps.tracker.subscriber.EventListener;

@RunWith(SpringRunner.class)
public class EventListenerTest {

	@Mock
	private PositionService service;

	@InjectMocks
	private EventListener subscriber;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void subscriberCallTest() {
		Message<PositionDto> message = MessageBuilder.withPayload(new PositionDto()).build();
		subscriber.consume(message);
		Mockito.verify(service, Mockito.atLeast(1))
				.save(Mockito.any(PositionDto.class));
	}
}
