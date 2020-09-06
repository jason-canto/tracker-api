package br.com.gps.tracker.test.dto;

import java.time.Instant;
import java.util.Date;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gps.tracker.dto.PositionDto;
import br.com.gps.tracker.model.Position;

public class PositionDtoTest {

	@Test
	public void positionDtoNotNullTest() {
		PositionDto positionDto = positionDtoBuilder();
		Assert.assertNotNull(positionDto.getPlateNumber());
		Assert.assertNotNull(positionDto.getContainerId());
		Assert.assertNotNull(positionDto.getContractId());
		Assert.assertNotNull(positionDto.getDate());
		Assert.assertNotNull(positionDto.getLatitude());
		Assert.assertNotNull(positionDto.getLongitude());
	}

	@Test
	public void equalsTest() {
		PositionDto positionDto = positionDtoBuilder();
		PositionDto copy = new PositionDto();
		copy.setContainerId(positionDto.getContainerId());
		copy.setDate(positionDto.getDate());
		copy.setLatitude(positionDto.getLatitude());
		copy.setLongitude(positionDto.getLongitude());
		copy.setPlateNumber(positionDto.getPlateNumber());
		copy.setContractId(positionDto.getContractId());

		Assert.assertEquals(copy.getContainerId(), positionDto.getContainerId());
		Assert.assertEquals(copy.getDate(), positionDto.getDate());
		Assert.assertEquals(copy.getLatitude(), positionDto.getLatitude());
		Assert.assertEquals(copy.getLongitude(), positionDto.getLongitude());
		Assert.assertEquals(copy.getPlateNumber(), positionDto.getPlateNumber());
		Assert.assertEquals(copy.getContractId(), positionDto.getContractId());

		Assert.assertTrue(positionDto.equals(copy));
		Assert.assertTrue(positionDto.equals(positionDto));
	}

	@Test
	public void notEqualsTest() {
		PositionDto positionDto = positionDtoBuilder();
		PositionDto otherPosition = new PositionDto();
		otherPosition.setContainerId("XYZ");
		otherPosition.setDate(new Date());
		otherPosition.setLatitude(Double.MIN_VALUE);
		otherPosition.setLongitude(Double.MIN_VALUE);
		otherPosition.setPlateNumber("XYZ");
		otherPosition.setContractId("XYZ");

		Assert.assertNotEquals(positionDto, otherPosition);
		Assert.assertNotEquals(null, otherPosition);
		Assert.assertFalse(positionDto.equals(null));
		Assert.assertFalse(positionDto.equals(new Object()));
	}

	@Test
	public void hashCodeTest() {
		PositionDto positionDto = positionDtoBuilder();
		Assert.assertNotNull(positionDto.hashCode());
	}

	@Test
	public void hashCodeNullValuesTest() {
		PositionDto positionDto = new PositionDto();
		Assert.assertNotNull(positionDto.hashCode());
	}

	@Test
	public void toStringTest() {
		PositionDto positionDto = positionDtoBuilder();
		Assert.assertNotNull(positionDto.toString());
		Assert.assertTrue(positionDto.toString().startsWith(PositionDto.class.getSimpleName()));
		Assert.assertTrue(positionDto.toString()
				.endsWith("(PlateNumber=5143SAS, ContractId=52548445, "
						+ "ContainerId=RXG45741648, latitude=1.7976931348623157E308, "
						+ "longitude=1.7976931348623157E308, "
						+ "Date=Thu Jul 09 19:44:18 BRT 2020)"));
	}

	@Test
	public void serializationTest() {
		ObjectMapper mapper = new ObjectMapper();
		PositionDto original = positionDtoBuilder();
		PositionDto copy = SerializationUtils.clone(original);
		Assert.assertEquals(original, copy);
		Assert.assertTrue(mapper.canSerialize(PositionDto.class));
		Assert.assertTrue(mapper.canDeserialize(mapper.constructType(PositionDto.class)));
	}

	@Test
	public void entityToDtoTest() {
		ModelMapper mapper = new ModelMapper();
		Position position = positionEntityBuilder();
		PositionDto positionDto = mapper.map(position, PositionDto.class);
		Assert.assertEquals(positionDto.getContainerId(), position.getContainerId());
		Assert.assertEquals(positionDto.getDate(), position.getDate());
		Assert.assertEquals(positionDto.getLatitude(), position.getLatitude());
		Assert.assertEquals(positionDto.getLongitude(), position.getLongitude());
		Assert.assertEquals(positionDto.getPlateNumber(), position.getPlateNumber());
		Assert.assertEquals(positionDto.getContractId(), position.getContractId());
	}

	@Test
	public void dtoToEntityTest() {
		ModelMapper mapper = new ModelMapper();
		PositionDto positionDto = positionDtoBuilder();
		Position position = mapper.map(positionDto, Position.class);
		Assert.assertEquals(position.getContainerId(), positionDto.getContainerId());
		Assert.assertEquals(position.getDate(), positionDto.getDate());
		Assert.assertEquals(position.getLatitude(), positionDto.getLatitude());
		Assert.assertEquals(position.getLongitude(), positionDto.getLongitude());
		Assert.assertEquals(position.getPlateNumber(), positionDto.getPlateNumber());
		Assert.assertEquals(position.getContractId(), positionDto.getContractId());
	}

	private PositionDto positionDtoBuilder() {
		PositionDto positionDto = new PositionDto();
		positionDto.setContainerId("RXG45741648");
		positionDto.setDate(Date.from(Instant.parse("2020-07-09T22:44:18.391Z")));
		positionDto.setLatitude(Double.MAX_VALUE);
		positionDto.setLongitude(Double.MAX_VALUE);
		positionDto.setPlateNumber("5143SAS");
		positionDto.setContractId("52548445");
		return positionDto;
	}

	private Position positionEntityBuilder() {
		Position position = new Position();
		position.setContainerId("RXG45741648");
		position.setDate(Date.from(Instant.parse("2020-07-09T22:44:18.391Z")));
		position.setLatitude(Double.MAX_VALUE);
		position.setLongitude(Double.MAX_VALUE);
		position.setPlateNumber("5143SAS");
		position.setContractId("52548445");
		position.setCreatedDate(Date.from(Instant.parse("2020-07-09T22:44:18.391Z")));
		position.setUpdateDate(Date.from(Instant.parse("2020-07-09T22:44:18.391Z")));
		return position;
	}

	@Test
	public void notEqualsByContainerIdTest() {
		PositionDto positionDto = positionDtoBuilder();
		PositionDto copy = new PositionDto();
		copy.setContainerId("123");
		copy.setDate(positionDto.getDate());
		copy.setLatitude(positionDto.getLatitude());
		copy.setLongitude(positionDto.getLongitude());
		copy.setPlateNumber(positionDto.getPlateNumber());
		copy.setContractId(positionDto.getContractId());

		Assert.assertFalse(positionDto.equals(copy));
		copy.setContainerId(null);
		Assert.assertFalse(copy.equals(positionDto));
	}

	@Test
	public void notEqualsByDateTest() {
		PositionDto positionDto = positionDtoBuilder();
		PositionDto copy = new PositionDto();
		copy.setContainerId(positionDto.getContainerId());
		copy.setDate(Date.from(Instant.parse("2007-12-03T10:15:30.00Z")));
		copy.setLatitude(positionDto.getLatitude());
		copy.setLongitude(positionDto.getLongitude());
		copy.setPlateNumber(positionDto.getPlateNumber());
		copy.setContractId(positionDto.getContractId());

		Assert.assertFalse(positionDto.equals(copy));
		copy.setDate(null);
		Assert.assertFalse(copy.equals(positionDto));
	}

	@Test
	public void notEqualsByLatitudeTest() {
		PositionDto positionDto = positionDtoBuilder();
		PositionDto copy = new PositionDto();
		copy.setContainerId(positionDto.getContainerId());
		copy.setDate(positionDto.getDate());
		copy.setLatitude(Double.MIN_VALUE);
		copy.setLongitude(positionDto.getLongitude());
		copy.setPlateNumber(positionDto.getPlateNumber());
		copy.setContractId(positionDto.getContractId());

		Assert.assertFalse(positionDto.equals(copy));
		copy.setLatitude(null);
		Assert.assertFalse(copy.equals(positionDto));
	}

	@Test
	public void notEqualsByLongitudeTest() {
		PositionDto positionDto = positionDtoBuilder();
		PositionDto copy = new PositionDto();
		copy.setContainerId(positionDto.getContainerId());
		copy.setDate(positionDto.getDate());
		copy.setLatitude(positionDto.getLatitude());
		copy.setLongitude(Double.MIN_VALUE);
		copy.setPlateNumber(positionDto.getPlateNumber());
		copy.setContractId(positionDto.getContractId());

		Assert.assertFalse(positionDto.equals(copy));
		copy.setLongitude(null);
		Assert.assertFalse(copy.equals(positionDto));
	}

	@Test
	public void notEqualsByPlateNumberTest() {
		PositionDto positionDto = positionDtoBuilder();
		PositionDto copy = new PositionDto();
		copy.setContainerId(positionDto.getContainerId());
		copy.setDate(positionDto.getDate());
		copy.setLatitude(positionDto.getLatitude());
		copy.setLongitude(positionDto.getLongitude());
		copy.setPlateNumber("123");
		copy.setContractId(positionDto.getContractId());

		Assert.assertFalse(positionDto.equals(copy));
		copy.setPlateNumber(null);
		Assert.assertFalse(copy.equals(positionDto));
	}

	@Test
	public void notEqualsByContractIdTest() {
		PositionDto positionDto = positionDtoBuilder();
		PositionDto copy = new PositionDto();
		copy.setContainerId(positionDto.getContainerId());
		copy.setDate(positionDto.getDate());
		copy.setLatitude(positionDto.getLatitude());
		copy.setLongitude(positionDto.getLongitude());
		copy.setPlateNumber(positionDto.getPlateNumber());
		copy.setContractId("123");

		Assert.assertFalse(positionDto.equals(copy));
		copy.setContractId(null);
		Assert.assertFalse(copy.equals(positionDto));
	}

	@Test
	public void testEqualsEmptyValue() {
		PositionDto a = new PositionDto();
		PositionDto b = new PositionDto();
		Assert.assertTrue(a.equals(b) && b.equals(a));
		Assert.assertTrue(a.toString().equals(b.toString()));
		Assert.assertTrue(a.hashCode() == b.hashCode());
		Assert.assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void testEqualsNull(){
		PositionDto emptyPosition = new PositionDto();
		PositionDto nullPosition = null;
		PositionDto positionDto = positionDtoBuilder();

		Assert.assertFalse(positionDto.equals(null));
		Assert.assertFalse(positionDto.equals(nullPosition));
		Assert.assertFalse(positionDto.equals(emptyPosition));
		Assert.assertTrue(positionDto.equals(positionDto));
		Assert.assertFalse(emptyPosition.equals(null));
		Assert.assertFalse(emptyPosition.equals(nullPosition));
	}

	@Test
	public void testNoArgsConstructor() {
		PositionDto emptyPosition = new PositionDto();
		Assert.assertNotNull(emptyPosition);
	}
}
