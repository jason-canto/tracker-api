package br.com.gps.tracker.test.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.gps.tracker.dto.PositionDto;
import br.com.gps.tracker.model.Position;
import br.com.gps.tracker.repository.PositionRepository;
import br.com.gps.tracker.service.PositionService;

@RunWith(MockitoJUnitRunner.class)
public class PositionServiceIntegrationTest {

	@Mock
	private PositionRepository positionRepository;

	@Spy
	private ModelMapper modelMapper;

	@InjectMocks
	private PositionService positionService;

	@Before
	public void setUp() {
		when(positionRepository.findAll()).thenReturn(generateFakePositionsAsList());
		when(positionRepository.findById(1L)).thenReturn(generateFakePositionOptional());
		when(positionRepository.save(isA(Position.class))).thenReturn(generateFakePosition(Instant.now(), null));
		when(positionRepository.searchByTimeFrame(any(Date.class), isA(Pageable.class))).thenReturn(generateFakePositionsPageable());
		when(positionRepository.searchByWorkOrder(eq("brmr123"), isA(Pageable.class))).thenReturn(generateFakePositionsPageable());
		when(positionRepository.searchByWorkOrder(eq("blah"), isA(Pageable.class))).thenReturn(Page.empty());
	}

	private Position generateFakePosition(Instant now, Long id) {
		Position pos = new Position();
		pos.setId(id != null ? id : 1L);
		pos.setContractId("BRMR123");
		pos.setContainerId("MSK123");
		pos.setPlateNumber("ABC1234");
		pos.setLatitude(12.123456);
		pos.setLongitude(-12.987654);
		pos.setDate(Date.from(now.plusSeconds(60)));
		pos.setCreatedDate(Date.from(now.plusSeconds(60)));
		pos.setUpdateDate(Date.from(now.plusSeconds(60)));
		return pos;
	}

	private Optional<Position> generateFakePositionOptional() {
		return Optional.of(this.generateFakePosition(Instant.now(), null));
	}

	private List<Position> generateFakePositionsAsList() {
		Instant now = Instant.now();
		List<Position> positions = new ArrayList<Position>();
		for(int i = 0; i < 1000; i++) {
			positions.add(generateFakePosition(now, new Long(i)));
			now = now.plusSeconds(60);
		}
		return positions;
	}

	private Page<Position> generateFakePositionsPageable() {
		return new PageImpl<Position>(generateFakePositionsAsList());
	}

	@Test
	public void whenFindByIdPosition_thenReturnPosition() {
		PositionDto dto = new PositionDto();
		dto.setContainerId("MSK123");
		dto.setDate(new Date());
		dto.setLatitude(12.123456);
		dto.setLongitude(-12.987654);
		dto.setPlateNumber("ABC1234");
		dto.setContractId("BRMR123");

		Optional<PositionDto> save = positionService.findById(1L);
		Assert.assertTrue(save.isPresent());
		Assert.assertTrue(save.get().getPlateNumber().equalsIgnoreCase(dto.getPlateNumber()));
		Assert.assertTrue(save.get().getContractId().equalsIgnoreCase(dto.getContractId()));
		Assert.assertTrue(save.get().getContainerId().equalsIgnoreCase(dto.getContainerId()));
		Assert.assertTrue(save.get().getLatitude().equals(dto.getLatitude()));
		Assert.assertTrue(save.get().getLongitude().equals(dto.getLongitude()));
	}

	@Test
	public void whenFindAll_thenReturnPositionDtoPageable() {
		Page<PositionDto> positionsPage = positionService.findAll(1, 10);
		Assert.assertTrue(positionsPage.hasContent());
		Assert.assertTrue(positionsPage.getTotalElements() == 1010);
		Assert.assertTrue(positionsPage.getTotalPages() == 101);
		Assert.assertTrue(positionsPage.getNumberOfElements() == 1000);
		Assert.assertTrue(positionsPage.getContent().get(0).getPlateNumber().equalsIgnoreCase("abc1234"));
	}

	@Test
	public void whenSavePosition_thenReturnNewPosition() {
		PositionDto dto = new PositionDto();
		dto.setContainerId("MSK123");
		dto.setDate(new Date());
		dto.setLatitude(12.123456);
		dto.setLongitude(-12.987654);
		dto.setPlateNumber("ABC1234");
		dto.setContractId("BRMR123");

		Position save = positionService.save(dto);
		Assert.assertTrue(save.getId().equals(1L));
		Assert.assertTrue(save.getPlateNumber().equalsIgnoreCase(dto.getPlateNumber()));
		Assert.assertTrue(save.getContractId().equalsIgnoreCase(dto.getContractId()));
		Assert.assertTrue(save.getContainerId().equalsIgnoreCase(dto.getContainerId()));
		Assert.assertTrue(save.getLatitude().equals(dto.getLatitude()));
		Assert.assertTrue(save.getLongitude().equals(dto.getLongitude()));
	}

	@Test
	public void whenSearchByWorkOrder_thenReturnPosition() {
		Page<PositionDto> positionsPage = positionService.searchByWorkOrder("BRMR123", 0, 10);
		Assert.assertTrue(positionsPage.hasContent());
		Assert.assertTrue(positionsPage.getTotalElements() == 1000);
		Assert.assertTrue(positionsPage.getTotalPages() == 1);
		Assert.assertTrue(positionsPage.getNumberOfElements() == 1000);
		Assert.assertTrue(positionsPage.getContent().get(0).getPlateNumber().equalsIgnoreCase("abc1234"));
	}

	@Test
	public void whenSearchByTimeFrame_thenReturnPosition() {
		Page<PositionDto> positionsPage = positionService.searchByTimeFrame(15, 0, 10);
		Assert.assertTrue(positionsPage.hasContent());
		Assert.assertTrue(positionsPage.getTotalElements() == 1000);
		Assert.assertTrue(positionsPage.getTotalPages() == 1);
		Assert.assertTrue(positionsPage.getNumberOfElements() == 1000);
		Assert.assertTrue(positionsPage.getContent().get(0).getPlateNumber().equalsIgnoreCase("abc1234"));
	}

	@Test
	public void whenSearchByWrongWorkOrder_thenReturnEmpty() {
		Page<PositionDto> positionsPage = positionService.searchByWorkOrder("blah", 0, 10);
		Assert.assertTrue(positionsPage.isEmpty());
	}
}
