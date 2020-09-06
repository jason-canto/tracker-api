package br.com.gps.tracker.test.repository;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gps.tracker.exception.PositionNotFoundException;
import br.com.gps.tracker.model.Position;
import br.com.gps.tracker.repository.PositionRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integration-tests.properties")
public class PositionRepositoryIntegrationTest {

	@Autowired
	private PositionRepository positionRepository;

	@Before
	public void setUp() {
		createMockPositions();
	}

	private void createMockPositions() {
		Instant now = Instant.now();
		List<Position> positions = new ArrayList<Position>();
		for(int i = 0; i < 1000; i++) {
			Position pos = new Position();
			pos.setContractId("BRMR123");
			pos.setContainerId("MSK123");
			pos.setPlateNumber("ABC1234");
			pos.setLatitude(12.123456);
			pos.setLongitude(-12.123987);
			pos.setDate(Date.from(now.minus(15, ChronoUnit.MINUTES)));
			pos.setCreatedDate(Date.from(now.plusSeconds(60)));
			pos.setUpdateDate(Date.from((now.plusSeconds(60))));
			now = now.minusSeconds(60);
			positions.add(pos);
		}

		positionRepository.saveAll(positions);
	}

	@After
	public void setDown() {
		positionRepository.deleteAll();
	}

	@Test
	public void whenSearchByTimeFrame_thenReturnPosition() {
		Date dateToSearch = Date.from(Instant.now().minus(20, ChronoUnit.MINUTES));
		Page<Position> positionPage = positionRepository.searchByTimeFrame(dateToSearch, PageRequest.of(0, 10));
		Assert.assertTrue(positionPage.hasContent());
		Position returnedPosition = positionPage.getContent().get(0);
		Assert.assertTrue(returnedPosition.getContractId().equalsIgnoreCase("BRMR123"));
		Assert.assertTrue(returnedPosition.getPlateNumber().equalsIgnoreCase("abc1234"));
	}

	@Test
	public void whenSearchByContractId_thenReturnPosition() {
		Page<Position> positionPage = positionRepository.searchByWorkOrder("brmr123", PageRequest.of(0, 10));
		Assert.assertTrue(positionPage.hasContent());
		Position returnedPosition = positionPage.getContent().get(0);
		Assert.assertTrue(returnedPosition.getContractId().equalsIgnoreCase("BRMR123"));
		Assert.assertTrue(returnedPosition.getPlateNumber().equalsIgnoreCase("abc1234"));
	}

	@Test(expected = PositionNotFoundException.class)
	public void whenSearchByTimeFrame_thenNotFound() {
		Date dateToSearch = Date.from(Instant.now().minus(10, ChronoUnit.MINUTES));
		Page<Position> positionPage = positionRepository.searchByTimeFrame(dateToSearch, PageRequest.of(0, 10));
		Assert.assertFalse(positionPage.hasContent());
		throw new PositionNotFoundException();
	}

	@Test(expected = PositionNotFoundException.class)
	public void whenSearchByContractId_thenNotFound() {
		Page<Position> positionPage = positionRepository.searchByWorkOrder("1234561", PageRequest.of(0, 10));
		Assert.assertFalse(positionPage.hasContent());
		throw new PositionNotFoundException();
	}

	@Test
	public void whenDeletePositionsByDate() {
		Page<Position> positionsPreDelete = positionRepository.searchByWorkOrder("brmr123", PageRequest.of(0, 10));
		long olderTotal = positionsPreDelete.getTotalElements();
		positionRepository.deletePositionsExpiredByDate(Date.from(Instant.now().plus(Duration.ofHours(2))));
		Page<Position> positionsPosDelete = positionRepository.searchByWorkOrder("brmr123", PageRequest.of(0, 10));
		long newTotal = positionsPosDelete.getTotalElements();
		Assert.assertTrue(newTotal < olderTotal);
	}
}
