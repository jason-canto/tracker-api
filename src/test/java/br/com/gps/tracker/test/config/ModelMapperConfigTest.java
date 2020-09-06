package br.com.gps.tracker.test.config;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gps.tracker.config.MapperConfig;
import br.com.gps.tracker.dto.PositionDto;
import br.com.gps.tracker.model.Position;

@RunWith(SpringRunner.class)
public class ModelMapperConfigTest {

	@Test
	public void modelMapperDtoToEntityTest() {
		MapperConfig config = new MapperConfig();
		ModelMapper mapper = config.modelMapper();
		PositionDto positionDto = positionDtoMock();
		Position position = mapper.map(positionDto, Position.class);
		Assert.assertEquals(position.getContainerId(), positionDto.getContainerId());
		Assert.assertEquals(position.getDate(), positionDto.getDate());
		Assert.assertEquals(position.getLatitude(), positionDto.getLatitude());
		Assert.assertEquals(position.getLongitude(), positionDto.getLongitude());
		Assert.assertEquals(position.getPlateNumber(), positionDto.getPlateNumber());
		Assert.assertEquals(position.getContractId(), positionDto.getContractId());
	}

	@Test
	public void modelMapperEntityToDtoTest() {
		MapperConfig config = new MapperConfig();
		ModelMapper mapper = config.modelMapper();
		Position position = positionEntityMock();
		PositionDto positionDto = mapper.map(position, PositionDto.class);
		Assert.assertEquals(position.getContainerId(), positionDto.getContainerId());
		Assert.assertEquals(position.getDate(), positionDto.getDate());
		Assert.assertEquals(position.getLatitude(), positionDto.getLatitude());
		Assert.assertEquals(position.getLongitude(), positionDto.getLongitude());
		Assert.assertEquals(position.getPlateNumber(), positionDto.getPlateNumber());
		Assert.assertEquals(position.getContractId(), positionDto.getContractId());
	}

	private PositionDto positionDtoMock() {
		PositionDto positionDto = new PositionDto();
		positionDto.setContainerId("XYZ");
		positionDto.setDate(new Date());
		positionDto.setLatitude(Double.MAX_VALUE);
		positionDto.setLongitude(Double.MIN_VALUE);
		positionDto.setPlateNumber("123456");
		positionDto.setContractId("789");
		return positionDto;
	}

	private Position positionEntityMock() {
		Position position = new Position();
		position.setContainerId("XYZ");
		position.setDate(new Date());
		position.setLatitude(Double.MAX_VALUE);
		position.setLongitude(Double.MIN_VALUE);
		position.setPlateNumber("123456");
		position.setContractId("789");
		return position;
	}
}
