package br.com.gps.tracker.test.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gps.tracker.dto.PositionDto;

@RunWith(SpringRunner.class)
@EmbeddedKafka(topics = "topic", partitions = 1, controlledShutdown = true, brokerProperties = {"log.dir=target/embedded-kafka" })
@TestPropertySource(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
public class KafkaIntegrationTest {

	private static final String TEST_TOPIC = "testTopic";

	@Autowired
	EmbeddedKafkaBroker embeddedKafkaBroker;

	@Test
	public void testReceivingKafkaEvents() {
		Consumer<String, PositionDto> consumer = configureConsumer();
		Producer<String, PositionDto> producer = configureProducer();

		PositionDto dto = new PositionDto();
		dto.setContainerId("RXG45741648");
		dto.setDate(Date.from(Instant.parse("2020-07-09T22:44:18.000Z")));
		dto.setLatitude(Double.MAX_VALUE);
		dto.setLongitude(Double.MAX_VALUE);
		dto.setPlateNumber("5143SAS");
		dto.setContractId("52548445");

		producer.send(new ProducerRecord<>(TEST_TOPIC,"test-key", dto));

		ConsumerRecord<String, PositionDto> singleRecord = KafkaTestUtils.getSingleRecord(consumer, TEST_TOPIC);
		assertThat(singleRecord).isNotNull();
		assertThat(singleRecord.key()).isEqualTo("test-key");
		assertThat(singleRecord.value().getContainerId()).isEqualTo(dto.getContainerId());
		assertThat(singleRecord.value().getDate()).isEqualTo(dto.getDate());
		assertThat(singleRecord.value().getLatitude()).isEqualTo(dto.getLatitude());
		assertThat(singleRecord.value().getLongitude()).isEqualTo(dto.getLongitude());
		assertThat(singleRecord.value().getPlateNumber()).isEqualTo(dto.getPlateNumber());
		assertThat(singleRecord.value().getContractId()).isEqualTo(dto.getContractId());

		consumer.close();
		producer.close();
	}

	private Consumer<String, PositionDto> configureConsumer() {
		Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);
		consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "br.com.gps.tracker.dto");
		Consumer<String, PositionDto> consumer = new DefaultKafkaConsumerFactory<String, PositionDto>(consumerProps)
				.createConsumer();
		consumer.subscribe(Collections.singleton(TEST_TOPIC));
		return consumer;
	}

	private Producer<String, PositionDto> configureProducer() {
		Map<String, Object> producerProps = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
		producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		producerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "br.com.gps.tracker.dto");
		return new DefaultKafkaProducerFactory<String, PositionDto>(producerProps).createProducer();
	}
}