package br.com.gps.tracker.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.gps.tracker.dto.PositionDto;
import br.com.gps.tracker.model.Position;
import br.com.gps.tracker.service.PositionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EventListener {

	@Autowired
	private PositionService service;

	@KafkaListener(topics = "${topic.input.destination}")
	public void consume(@Payload Message<PositionDto> payload) {
		try {
			Acknowledgment ack = payload.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
			Position saved = service.save(payload.getPayload());
			if (saved != null && ack != null) {
				ack.acknowledge();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}