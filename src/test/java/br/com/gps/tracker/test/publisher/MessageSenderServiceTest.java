package br.com.gps.tracker.test.publisher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.gps.tracker.dto.RequestDto;
import br.com.gps.tracker.publisher.MessageSenderService;

@RunWith(SpringRunner.class)
public class MessageSenderServiceTest {

	@Mock
	private KafkaTemplate<String, RequestDto> kafkaTemplate;

	@InjectMocks
	private MessageSenderService messageService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(messageService, "topic", "topic-test");
	}

	@Test
	public void publisherCallTest() {
		messageService.sendMessage(new RequestDto());
		Mockito.verify(kafkaTemplate, Mockito.atLeast(1))
				.send(Mockito.anyString(), Mockito.any(RequestDto.class));
		Mockito.verify(kafkaTemplate, Mockito.atLeast(1)).flush();
	}
}
