package br.com.gps.tracker.test.dto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gps.tracker.dto.AuthUserDto;

public class AuthUserDtoTest {

	@Test
	public void userNotNullTest() {
		AuthUserDto userDto = userDtoBuilder();
		Assert.assertNotNull(userDto.getLogin());
		Assert.assertNotNull(userDto.getToken());
	}

	@Test
	public void equalsTest() {
		AuthUserDto userDto = userDtoBuilder();
		AuthUserDto copy = new AuthUserDto();
		copy.setLogin(userDto.getLogin());
		copy.setToken(userDto.getToken());

		Assert.assertEquals(copy.getLogin(), userDto.getLogin());
		Assert.assertEquals(copy.getToken(), userDto.getToken());
		Assert.assertTrue(userDto.equals(copy));
		Assert.assertTrue(copy.equals(userDto));
		Assert.assertTrue(userDto.equals(userDto));
	}

	@Test
	public void notEqualsTest() {
		AuthUserDto userDto = userDtoBuilder();
		AuthUserDto otherUser = new AuthUserDto();
		otherUser.setLogin("user2@test");
		otherUser.setToken("OtherToken");

		Map<String, Object> userMap = new HashMap<>();
		userMap.put("login", userDto.getLogin());
		userMap.put("token", userDto.getToken());
		userDto.setUser(userMap);

		Assert.assertNotEquals(otherUser, userDto);
		Assert.assertNotEquals(null, otherUser);
		Assert.assertFalse(userDto.equals(null));
		Assert.assertFalse(userDto.equals(new Object()));
	}

	@Test
	public void hashCodeTest() {
		AuthUserDto userDto = userDtoBuilder();
		Assert.assertNotNull(userDto.hashCode());
	}

	@Test
	public void hashCodeNullValuesTest() {
		AuthUserDto userDto = userDtoBuilder();
		Assert.assertNotNull(userDto.hashCode());
	}

	@Test
	public void toStringTest() {
		AuthUserDto userDto = userDtoBuilder();
		Assert.assertNotNull(userDto.toString());
		Assert.assertTrue(userDto.toString().startsWith(AuthUserDto.class.getSimpleName()));
		Assert.assertTrue(userDto.toString().endsWith("(login=user@test, token=XYZ)"));
	}

	@Test
	public void serializationTest() {
		ObjectMapper mapper = new ObjectMapper();
		AuthUserDto original = userDtoBuilder();
		AuthUserDto copy = SerializationUtils.clone(original);
		Assert.assertEquals(original, copy);
		Assert.assertTrue(mapper.canSerialize(AuthUserDto.class));
		Assert.assertTrue(mapper.canDeserialize(mapper.constructType(AuthUserDto.class)));
	}

	@Test
	public void deserializationTest() throws JsonParseException, JsonMappingException, IOException {
		String json = "{\"type\":\"UsuarioTO\","
						+ "\"usuario\":{\"login\":\"XYZ@test\","
						+ "\"plataform\":\"XXX\","
						+ "\"token\":\"XHKJHK.242q4324242\","
						+ "\"tipoUsuario\":\"Z\"}}";
		AuthUserDto response = new ObjectMapper().readValue(json, AuthUserDto.class);

		Assert.assertEquals("XYZ@test", response.getLogin());
		Assert.assertEquals("XHKJHK.242q4324242", response.getToken());
	}

	private AuthUserDto userDtoBuilder() {
		AuthUserDto userDto = new AuthUserDto();
		userDto.setLogin("user@test");
		userDto.setToken("XYZ");

		Map<String, Object> userMap = new HashMap<>();
		userMap.put("login", userDto.getLogin());
		userMap.put("token", userDto.getToken());
		userDto.setUser(userMap);
		return userDto;
	}

	@Test
	public void notEqualsByTokenTest() {
		AuthUserDto userDto = userDtoBuilder();
		AuthUserDto otherUser = new AuthUserDto();
		otherUser.setLogin("user@test");
		otherUser.setToken("XYZ");

		Map<String, Object> userMap = new HashMap<>();
		userMap.put("login", userDto.getLogin());
		userMap.put("token", userDto.getToken());
		userDto.setUser(userMap);

		Assert.assertTrue(userDto.equals(otherUser));
		otherUser.setToken(null);
		Assert.assertFalse(otherUser.equals(userDto));
	}

	@Test
	public void notEqualsByLoginTest() {
		AuthUserDto userDto = userDtoBuilder();
		AuthUserDto otherUser = new AuthUserDto();
		otherUser.setLogin("user@test");
		otherUser.setToken("XYZ");

		Map<String, Object> userMap = new HashMap<>();
		userMap.put("login", userDto.getLogin());
		userMap.put("token", userDto.getToken());
		userDto.setUser(userMap);

		Assert.assertTrue(userDto.equals(otherUser));
		otherUser.setLogin("user2@test");
		Assert.assertFalse(otherUser.equals(userDto));
	}

	@Test
	public void testEqualsEmptyValue() {
		AuthUserDto a = new AuthUserDto();
		AuthUserDto b = new AuthUserDto();
		Assert.assertTrue(a.equals(b) && b.equals(a));
		Assert.assertTrue(a.toString().equals(b.toString()));
		Assert.assertTrue(a.hashCode() == b.hashCode());
		Assert.assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void testEqualsNull(){
		AuthUserDto emptyUser = new AuthUserDto();
		AuthUserDto nullUser = null;
		AuthUserDto userDto = userDtoBuilder();

		Assert.assertFalse(userDto.equals(null));
		Assert.assertFalse(userDto.equals(nullUser));
		Assert.assertFalse(userDto.equals(emptyUser));
		Assert.assertTrue(userDto.equals(userDto));
		Assert.assertFalse(emptyUser.equals(null));
		Assert.assertFalse(emptyUser.equals(nullUser));
	}

	@Test
	public void testNoArgsConstructor() {
		AuthUserDto emptyUser = new AuthUserDto();
		Assert.assertNotNull(emptyUser);
	}
}
