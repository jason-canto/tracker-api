package br.com.gps.tracker.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.gps.tracker.dto.RequestDto;

@Service
public class MessageSenderService {

	@Value("${topic.output.destination}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, RequestDto> producer;

	public void sendMessage(RequestDto eventProduce) {
		producer.send(topic, eventProduce);
		producer.flush();
	}
}