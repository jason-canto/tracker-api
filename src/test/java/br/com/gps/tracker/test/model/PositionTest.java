package br.com.gps.tracker.test.model;

import java.time.Instant;
import java.util.Date;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gps.tracker.model.Position;

public class PositionTest {
	
	@Test
	public void positionNotNullTest() {
		Position position = positionBuilder();
		Assert.assertNotNull(position.getPlateNumber());
		Assert.assertNotNull(position.getContainerId());
		Assert.assertNotNull(position.getContractId());
		Assert.assertNotNull(position.getDate());
		Assert.assertNotNull(position.getLatitude());
		Assert.assertNotNull(position.getLongitude());
		Assert.assertNotNull(position.getCreatedDate());
		Assert.assertNotNull(position.getUpdateDate());
	}

	@Test
	public void equalsTest() {
		Position position = positionBuilder();
		Position copy = new Position();
		copy.setContainerId(position.getContainerId());
		copy.setDate(position.getDate());
		copy.setLatitude(position.getLatitude());
		copy.setLongitude(position.getLongitude());
		copy.setPlateNumber(position.getPlateNumber());
		copy.setContractId(position.getContractId());
		copy.setCreatedDate(position.getCreatedDate());
		copy.setUpdateDate(position.getUpdateDate());

		Assert.assertEquals(copy.getContainerId(), position.getContainerId());
		Assert.assertEquals(copy.getDate(), position.getDate());
		Assert.assertEquals(copy.getLatitude(), position.getLatitude());
		Assert.assertEquals(copy.getLongitude(), position.getLongitude());
		Assert.assertEquals(copy.getPlateNumber(), position.getPlateNumber());
		Assert.assertEquals(copy.getContractId(), position.getContractId());
		Assert.assertEquals(copy.getCreatedDate(), position.getCreatedDate());
		Assert.assertEquals(copy.getUpdateDate(), position.getUpdateDate());

		Assert.assertTrue(position.equals(copy));
		Assert.assertTrue(position.equals(position));
	}

	@Test
	public void notEqualsTest() {
		Position position = positionBuilder();
		Position otherPosition = new Position();
		otherPosition.setContainerId("XYZ");
		otherPosition.setDate(new Date());
		otherPosition.setLatitude(Double.MIN_VALUE);
		otherPosition.setLongitude(Double.MIN_VALUE);
		otherPosition.setPlateNumber("XYZ");
		otherPosition.setContractId("XYZ");

		Assert.assertNotEquals(position, otherPosition);
		Assert.assertNotEquals(null, otherPosition);
		Assert.assertFalse(position.equals(null));
		Assert.assertFalse(position.equals(new Object()));
	}

	@Test
	public void hashCodeTest() {
		Position position = positionBuilder();
		position.setId(1L);
		Assert.assertNotNull(position.hashCode());
	}
	
	@Test
	public void hashCodeNullValuesTest() {
		Position position = new Position();
		Assert.assertNotNull(position.hashCode());
	}

	@Test
	public void toStringTest() {
		Position position = positionBuilder();
		Assert.assertNotNull(position.toString());
		Assert.assertTrue(position.toString().startsWith(Position.class.getSimpleName()));
		Assert.assertTrue(position.toString()
				.endsWith("(id=null, ContractId=52548445, "
						+ "ContainerId=RXG45741648, PlateNumber=5143SAS, "
						+ "latitude=1.7976931348623157E308, "
						+ "longitude=1.7976931348623157E308, "
						+ "Date=Thu Jul 09 19:44:18 BRT 2020, "
						+ "createdDate=Thu Jul 09 19:44:18 BRT 2020, "
						+ "updateDate=Thu Jul 09 19:44:18 BRT 2020)"));
	}

	@Test
	public void serializationTest() {
		ObjectMapper mapper = new ObjectMapper();
		Position original = positionBuilder();
		Position copy = SerializationUtils.clone(original);
		Assert.assertEquals(original, copy);
		Assert.assertTrue(mapper.canSerialize(Position.class));
		Assert.assertTrue(mapper.canDeserialize(mapper.constructType(Position.class)));
	}

	private Position positionBuilder() {
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
		Position position = positionBuilder();
		Position copy = new Position();
		copy.setContainerId("123");
		copy.setDate(position.getDate());
		copy.setLatitude(position.getLatitude());
		copy.setLongitude(position.getLongitude());
		copy.setPlateNumber(position.getPlateNumber());
		copy.setContractId(position.getContractId());
		copy.setCreatedDate(position.getCreatedDate());
		copy.setUpdateDate(position.getUpdateDate());

		Assert.assertFalse(position.equals(copy));
		copy.setContainerId(null);
		Assert.assertFalse(copy.equals(position));
	}

	@Test
	public void notEqualsByDateTest() {
		Position position = positionBuilder();
		Position copy = new Position();
		copy.setContainerId(position.getContainerId());
		copy.setDate(Date.from(Instant.parse("2007-12-03T10:15:30.00Z")));
		copy.setLatitude(position.getLatitude());
		copy.setLongitude(position.getLongitude());
		copy.setPlateNumber(position.getPlateNumber());
		copy.setContractId(position.getContractId());
		copy.setCreatedDate(position.getCreatedDate());
		copy.setUpdateDate(position.getUpdateDate());

		Assert.assertFalse(position.equals(copy));
		copy.setDate(null);
		Assert.assertFalse(copy.equals(position));
	}

	@Test
	public void notEqualsByLatitudeTest() {
		Position position = positionBuilder();
		Position copy = new Position();
		copy.setContainerId(position.getContainerId());
		copy.setDate(position.getDate());
		copy.setLatitude(Double.MIN_VALUE);
		copy.setLongitude(position.getLongitude());
		copy.setPlateNumber(position.getPlateNumber());
		copy.setContractId(position.getContractId());
		copy.setCreatedDate(position.getCreatedDate());
		copy.setUpdateDate(position.getUpdateDate());

		Assert.assertFalse(position.equals(copy));
		copy.setLatitude(null);
		Assert.assertFalse(copy.equals(position));
	}

	@Test
	public void notEqualsByLongitudeTest() {
		Position position = positionBuilder();
		Position copy = new Position();
		copy.setContainerId(position.getContainerId());
		copy.setDate(position.getDate());
		copy.setLatitude(position.getLatitude());
		copy.setLongitude(Double.MIN_VALUE);
		copy.setPlateNumber(position.getPlateNumber());
		copy.setContractId(position.getContractId());
		copy.setCreatedDate(position.getCreatedDate());
		copy.setUpdateDate(position.getUpdateDate());

		Assert.assertFalse(position.equals(copy));
		copy.setLongitude(null);
		Assert.assertFalse(copy.equals(position));
	}

	@Test
	public void notEqualsByPlateNumberTest() {
		Position position = positionBuilder();
		Position copy = new Position();
		copy.setContainerId(position.getContainerId());
		copy.setDate(position.getDate());
		copy.setLatitude(position.getLatitude());
		copy.setLongitude(position.getLongitude());
		copy.setPlateNumber("123");
		copy.setContractId(position.getContractId());
		copy.setCreatedDate(position.getCreatedDate());
		copy.setUpdateDate(position.getUpdateDate());

		Assert.assertFalse(position.equals(copy));
		copy.setPlateNumber(null);
		Assert.assertFalse(copy.equals(position));
	}

	@Test
	public void notEqualsByContractIdTest() {
		Position position = positionBuilder();
		Position copy = new Position();
		copy.setContainerId(position.getContainerId());
		copy.setDate(position.getDate());
		copy.setLatitude(position.getLatitude());
		copy.setLongitude(position.getLongitude());
		copy.setPlateNumber(position.getPlateNumber());
		copy.setContractId("123");
		copy.setCreatedDate(position.getCreatedDate());
		copy.setUpdateDate(position.getUpdateDate());

		Assert.assertFalse(position.equals(copy));
		copy.setContractId(null);
		Assert.assertFalse(copy.equals(position));
	}

	@Test
	public void notEqualsByCreateDateTest() {
		Position position = positionBuilder();
		Position copy = new Position();
		copy.setContainerId(position.getContainerId());
		copy.setDate(position.getDate());
		copy.setLatitude(position.getLatitude());
		copy.setLongitude(position.getLongitude());
		copy.setPlateNumber(position.getPlateNumber());
		copy.setContractId(position.getContractId());
		copy.setCreatedDate(Date.from(Instant.parse("2007-12-03T10:15:30.00Z")));
		copy.setUpdateDate(position.getUpdateDate());

		Assert.assertFalse(position.equals(copy));
		copy.setCreatedDate(null);
		Assert.assertFalse(copy.equals(position));
	}

	@Test
	public void notEqualsByUpdateDateTest() {
		Position position = positionBuilder();
		Position copy = new Position();
		copy.setContainerId(position.getContainerId());
		copy.setDate(position.getDate());
		copy.setLatitude(position.getLatitude());
		copy.setLongitude(position.getLongitude());
		copy.setPlateNumber(position.getPlateNumber());
		copy.setContractId(position.getContractId());
		copy.setCreatedDate(position.getCreatedDate());
		copy.setUpdateDate(Date.from(Instant.parse("2007-12-03T10:15:30.00Z")));

		Assert.assertFalse(position.equals(copy));
		copy.setUpdateDate(null);
		Assert.assertFalse(copy.equals(position));
	}

	@Test
	public void notEqualsByIdTest() {
		Position position = positionBuilder();
		Position copy = new Position();
		position.setId(1L);
		copy.setId(2L);
		copy.setContainerId(position.getContainerId());
		copy.setDate(position.getDate());
		copy.setLatitude(position.getLatitude());
		copy.setLongitude(position.getLongitude());
		copy.setPlateNumber(position.getPlateNumber());
		copy.setContractId(position.getContractId());
		copy.setCreatedDate(position.getCreatedDate());
		copy.setUpdateDate(position.getUpdateDate());

		Assert.assertFalse(position.equals(copy));
		copy.setId(null);
		Assert.assertFalse(copy.equals(position));
	}

	@Test
	public void testEqualsEmptyValue() {
		Position a = new Position();
		Position b = new Position();
		Assert.assertTrue(a.equals(b) && b.equals(a));
		Assert.assertTrue(a.toString().equals(b.toString()));
		Assert.assertTrue(a.hashCode() == b.hashCode());
		Assert.assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void testEqualsNull(){
		Position emptyPosition = new Position();
		Position nullPosition = null;
		Position position = positionBuilder();

		Assert.assertFalse(position.equals(null));
		Assert.assertFalse(position.equals(nullPosition));
		Assert.assertFalse(position.equals(emptyPosition));
		Assert.assertTrue(position.equals(position));
		Assert.assertFalse(emptyPosition.equals(null));
		Assert.assertFalse(emptyPosition.equals(nullPosition));
	}

	@Test
	public void testNoArgsConstructor() {
		Position emptyPosition = new Position();
		Assert.assertNotNull(emptyPosition);
	}

}
